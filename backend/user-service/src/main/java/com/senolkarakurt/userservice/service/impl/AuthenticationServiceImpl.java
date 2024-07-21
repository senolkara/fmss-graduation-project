package com.senolkarakurt.userservice.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.senolkarakurt.dto.request.RegistrationRequestDto;
import com.senolkarakurt.dto.request.SystemLogSaveRequestDto;
import com.senolkarakurt.dto.response.AddressResponseDto;
import com.senolkarakurt.dto.response.UserResponseDto;
import com.senolkarakurt.enums.RecordStatus;
import com.senolkarakurt.exception.CommonException;
import com.senolkarakurt.userservice.config.JwtService;
import com.senolkarakurt.userservice.converter.AddressConverter;
import com.senolkarakurt.userservice.converter.UserConverter;
import com.senolkarakurt.userservice.dto.request.AuthenticationRequestDto;
import com.senolkarakurt.userservice.dto.response.AuthenticationResponseDto;
import com.senolkarakurt.userservice.dto.response.RegistrationResponseDto;
import com.senolkarakurt.userservice.exception.ExceptionMessagesResource;
import com.senolkarakurt.userservice.model.Address;
import com.senolkarakurt.userservice.model.User;
import com.senolkarakurt.userservice.repository.AddressRepository;
import com.senolkarakurt.userservice.repository.UserRepository;
import com.senolkarakurt.userservice.service.AuthenticationService;
import com.senolkarakurt.userservice.service.SystemLogService;
import com.senolkarakurt.userservice.token.Token;
import com.senolkarakurt.userservice.token.TokenRepository;
import com.senolkarakurt.userservice.token.TokenType;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

@Service
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final ExceptionMessagesResource exceptionMessagesResource;
    private final SystemLogService systemLogService;

    public AuthenticationServiceImpl(UserRepository userRepository,
                                     AddressRepository addressRepository,
                                     TokenRepository tokenRepository,
                                     PasswordEncoder passwordEncoder,
                                     JwtService jwtService,
                                     AuthenticationManager authenticationManager,
                                     ExceptionMessagesResource exceptionMessagesResource,
                                     @Qualifier("dbLogService") SystemLogService systemLogService) {
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
        this.tokenRepository = tokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.exceptionMessagesResource = exceptionMessagesResource;
        this.systemLogService = systemLogService;
    }

    @Override
    public RegistrationResponseDto register(RegistrationRequestDto registrationRequestDto) {
        Optional<User> userOptional = userRepository.findByEmail(registrationRequestDto.getEmail());
        if (userOptional.isPresent()){
            log.error("%s : {} %s".formatted(exceptionMessagesResource.getEmailAlreadyExist(), registrationRequestDto.getEmail()));
            saveSystemLog("%s : {} %s".formatted(exceptionMessagesResource.getEmailAlreadyExist(), registrationRequestDto.getEmail()));
            throw new CommonException(exceptionMessagesResource.getEmailAlreadyExist());
        }
        User user = UserConverter.toUserByRegistrationRequestDto(registrationRequestDto);
        user.setPassword(passwordEncoder.encode(registrationRequestDto.getPassword()));
        userRepository.save(user);
        String jwtToken = jwtService.generateToken(user);
        jwtService.generateRefreshToken(user);
        saveUserToken(user, jwtToken);
        return RegistrationResponseDto.builder()
                .user(user)
                .build();
    }

    @Override
    public AuthenticationResponseDto authenticate(AuthenticationRequestDto authenticationRequestDto) {
        Optional<User> userOptional = userRepository.findByEmail(authenticationRequestDto.getEmail());
        if (userOptional.isEmpty()){
            log.error("%s : {}".formatted(exceptionMessagesResource.getUserNotFoundWithThisEmail()));
            saveSystemLog("%s : {}".formatted(exceptionMessagesResource.getUserNotFoundWithThisEmail()));
            throw new CommonException(exceptionMessagesResource.getUserNotFoundWithThisEmail());
        }
        User user = userOptional.get();
        if (!user.getRecordStatus().equals(RecordStatus.ACTIVE)){
            log.error("%s : {}".formatted(exceptionMessagesResource.getThisUserIsInActive()));
            saveSystemLog("%s : {}".formatted(exceptionMessagesResource.getThisUserIsInActive()));
            throw new CommonException(exceptionMessagesResource.getThisUserIsInActive());
        }
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequestDto.getEmail(),
                        authenticationRequestDto.getPassword()
                )
        );
        if (!authenticate.isAuthenticated()){
            log.error("%s : {}".formatted(exceptionMessagesResource.getLoginFailed()));
            saveSystemLog("%s : {}".formatted(exceptionMessagesResource.getLoginFailed()));
            throw new CommonException(exceptionMessagesResource.getLoginFailed());
        }
        String jwtToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        UserResponseDto userResponseDto = UserConverter.toUserResponseDtoByUser(user);
        Set<Address> addresses = new HashSet<>(addressRepository.findByUserIdAndRecordStatus(user.getId(), RecordStatus.ACTIVE));
        Set<AddressResponseDto> addressResponseDtoSet = AddressConverter.toSetAddressesResponseDtoBySetAddresses(addresses);
        userResponseDto.setAddressResponseDtoSet(addressResponseDtoSet);
        return AuthenticationResponseDto.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .userResponseDto(userResponseDto)
                .user(user)
                .build();
    }

    @Override
    public void saveUserToken(User user, String jwtToken) {
        Token token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    @Override
    public void revokeAllUserTokens(User user) {
        List<Token> validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    @Override
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);
        if (userEmail != null) {
            User user = userRepository.findByEmail(userEmail)
                    .orElseThrow();
            if (jwtService.isTokenValid(refreshToken, user)) {
                String accessToken = jwtService.generateToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);
                UserResponseDto userResponseDto = UserConverter.toUserResponseDtoByUser(user);
                Set<Address> addresses = new HashSet<>(addressRepository.findByUserIdAndRecordStatus(user.getId(), RecordStatus.ACTIVE));
                Set<AddressResponseDto> addressResponseDtoSet = AddressConverter.toSetAddressesResponseDtoBySetAddresses(addresses);
                userResponseDto.setAddressResponseDtoSet(addressResponseDtoSet);
                AuthenticationResponseDto authenticationResponseDto = AuthenticationResponseDto.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .userResponseDto(userResponseDto)
                        .user(user)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authenticationResponseDto);
            }
        }
    }

    private void saveSystemLog(String content){
        SystemLogSaveRequestDto systemLogSaveRequestDto = SystemLogSaveRequestDto.builder()
                .recordDateTime(LocalDateTime.now())
                .content(content)
                .build();
        systemLogService.save(systemLogSaveRequestDto);
    }
}
