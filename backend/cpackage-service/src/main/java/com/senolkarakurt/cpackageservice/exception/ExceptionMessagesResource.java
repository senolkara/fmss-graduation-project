package com.senolkarakurt.cpackageservice.exception;

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

    @Value("${CUSTOMER_NOT_FOUND}")
    public String customerNotFound;

    @Value("${CUSTOMER_NOT_FOUND_WITH_ID}")
    public String customerNotFoundWithId;

    @Value("${CUSTOMER_NOT_FOUND_WITH_EMAIL}")
    public String customerNotFoundWithEmail;

    @Value("${PACKAGE_ALREADY_EXIST}")
    public String packageAlreadyExist;

    @Value("${PACKAGE_NOT_FOUND}")
    public String packageNotFound;

    @Value("${PACKAGE_NOT_FOUND_WITH_ID}")
    public String packageNotFoundWithId;

}
