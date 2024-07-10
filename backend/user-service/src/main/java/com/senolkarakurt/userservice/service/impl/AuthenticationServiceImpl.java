package com.senolkarakurt.userservice.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.senolkarakurt.dto.request.UserRequestDto;
import com.senolkarakurt.dto.response.AddressResponseDto;
import com.senolkarakurt.dto.response.UserResponseDto;
import com.senolkarakurt.enums.RecordStatus;
import com.senolkarakurt.exception.CommonException;
import com.senolkarakurt.userservice.config.JwtService;
import com.senolkarakurt.userservice.converter.AddressConverter;
import com.senolkarakurt.userservice.converter.UserConverter;
import com.senolkarakurt.userservice.dto.request.AuthenticationRequestDto;
import com.senolkarakurt.userservice.dto.response.AuthenticationResponseDto;
import com.senolkarakurt.userservice.exception.ExceptionMessagesResource;
import com.senolkarakurt.userservice.model.Address;
import com.senolkarakurt.userservice.model.User;
import com.senolkarakurt.userservice.repository.AddressRepository;
import com.senolkarakurt.userservice.repository.UserRepository;
import com.senolkarakurt.userservice.service.AuthenticationService;
import com.senolkarakurt.userservice.token.Token;
import com.senolkarakurt.userservice.token.TokenRepository;
import com.senolkarakurt.userservice.token.TokenType;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final ExceptionMessagesResource exceptionMessagesResource;

    @Override
    public User register(UserRequestDto userRequestDto) {
        Optional<User> userOptional = userRepository.findByEmail(userRequestDto.getEmail());
        if (userOptional.isPresent()){
            log.error("%s : {} %s".formatted(exceptionMessagesResource.getEmailAlreadyExist(), userRequestDto.getEmail()));
            throw new CommonException(exceptionMessagesResource.getEmailAlreadyExist());
        }
        Optional<User> userPhoneNumberOptional = userRepository.findByPhoneNumber(userRequestDto.getPhoneNumber());
        if (userPhoneNumberOptional.isPresent()){
            log.error("%s : {} %s".formatted(exceptionMessagesResource.getPhoneNumberAlreadyExist(), userRequestDto.getEmail()));
            throw new CommonException(exceptionMessagesResource.getPhoneNumberAlreadyExist());
        }
        User user = UserConverter.toUserByUserRequestDto(userRequestDto);
        user.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));
        userRepository.save(user);
        Set<Address> addressSet = AddressConverter.toSetAddressBySetAddressRequestDto(userRequestDto.getAddressRequestDtoSet(), user);
        addressRepository.saveAll(addressSet);
        String jwtToken = jwtService.generateToken(user);
        jwtService.generateRefreshToken(user);
        saveUserToken(user, jwtToken);
        return user;
    }

    @Override
    public UserResponseDto authenticate(AuthenticationRequestDto authenticationRequestDto) {
        Optional<User> userOptional = userRepository.findByEmail(authenticationRequestDto.getEmail());
        if (userOptional.isEmpty()){
            log.error("%s : {}".formatted(exceptionMessagesResource.getUserNotFoundWithThisEmail()));
            throw new CommonException(exceptionMessagesResource.getUserNotFoundWithThisEmail());
        }
        User user = userOptional.get();
        if (!user.getRecordStatus().equals(RecordStatus.ACTIVE)){
            log.error("%s : {}".formatted(exceptionMessagesResource.getThisUserIsInActive()));
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
            throw new CommonException(exceptionMessagesResource.getLoginFailed());
        }
        String jwtToken = jwtService.generateToken(user);
        jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        UserResponseDto userResponseDto = UserConverter.toUserResponseDtoByUser(user);
        Set<Address> addresses = new HashSet<>(addressRepository.findAddressesByUserIdAndRecordStatus(user.getId(), RecordStatus.ACTIVE));
        Set<AddressResponseDto> addressResponseDtoSet = AddressConverter.toSetAddressesResponseDtoBySetAddresses(addresses);
        userResponseDto.setAddressResponseDtoSet(addressResponseDtoSet);
        return userResponseDto;
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
                AuthenticationResponseDto authenticationResponseDto = AuthenticationResponseDto.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .user(user)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authenticationResponseDto);
            }
        }
    }
}
