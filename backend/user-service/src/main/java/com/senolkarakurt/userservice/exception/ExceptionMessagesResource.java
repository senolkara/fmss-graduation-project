package com.senolkarakurt.userservice.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Getter
@Component
@PropertySource("classpath:messages.properties")
@NoArgsConstructor
public class ExceptionMessagesResource {

    @Value("${EMAIL_ALREADY_EXIST}")
    public String emailAlreadyExist;

    @Value("${PHONE_NUMBER_ALREADY_EXIST}")
    public String phoneNumberAlreadyExist;

    @Value("${USER_NOT_FOUND_WITH_THIS_EMAIL}")
    public String userNotFoundWithThisEmail;

    @Value("${USER_NOT_FOUND_WITH_THIS_PHONE_NUMBER}")
    public String userNotFoundWithThisPhoneNumber;

    @Value("${USER_NOT_FOUND_WITH_ID}")
    public String userNotFoundWithId;

    @Value("${THIS_USER_IS_IN_ACTIVE}")
    public String thisUserIsInActive;

    @Value("${LOGIN_FAILED}")
    public String loginFailed;

    @Value("${NOT_LOGIN_INTO_SYSTEM}")
    public String notLoginIntoSystem;

}
