package com.senolkarakurt.userservice.service.impl;

import com.senolkarakurt.enums.RecordStatus;
import com.senolkarakurt.exception.CommonException;
import com.senolkarakurt.userservice.exception.ExceptionMessagesResource;
import com.senolkarakurt.userservice.model.Address;
import com.senolkarakurt.userservice.model.User;
import com.senolkarakurt.userservice.repository.AddressRepository;
import com.senolkarakurt.userservice.repository.UserRepository;
import com.senolkarakurt.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final ExceptionMessagesResource exceptionMessagesResource;

    @Override
    public User getUserById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()){
            log.error("%s : {} %s".formatted(exceptionMessagesResource.getUserNotFoundWithId(), id));
            throw new CommonException(exceptionMessagesResource.getUserNotFoundWithId());
        }
        if (!userOptional.get().getRecordStatus().equals(RecordStatus.ACTIVE)){
            log.error("%s : {} %s".formatted(exceptionMessagesResource.getThisUserIsInActive(), userOptional.get().getEmail()));
            throw new CommonException(exceptionMessagesResource.getThisUserIsInActive());
        }
        return userOptional.orElse(null);
    }

    @Override
    public Set<Address> getAddressesByUserId(Long userId) {
        return new HashSet<>(addressRepository.findAddressesByUserIdAndRecordStatus(userId, RecordStatus.ACTIVE));
    }

}
