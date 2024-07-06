package com.senolkarakurt.advertisementservice.exception;

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

    @Value("${BUILDING_ALREADY_EXIST}")
    public String buildingAlreadyExist;

    @Value("${BUILDING_NOT_FOUND}")
    public String buildingNotFound;

    @Value("${BUILDING_NOT_FOUND_WITH_ID}")
    public String buildingNotFoundWithId;

    @Value("${CUSTOMER_NOT_FOUND}")
    public String customerNotFound;

    @Value("${CUSTOMER_NOT_FOUND_WITH_ID}")
    public String customerNotFoundWithId;

    @Value("${CUSTOMER_NOT_FOUND_WITH_EMAIL}")
    public String customerNotFoundWithEmail;

    @Value("${ADVERTISEMENT_ALREADY_EXIST}")
    public String advertisementAlreadyExist;

    @Value("${ADVERTISEMENT_NOT_FOUND}")
    public String advertisementNotFound;

    @Value("${ADVERTISEMENT_NOT_FOUND_WITH_ID}")
    public String advertisementNotFoundWithId;

    @Value("${PACKAGE_ALREADY_EXIST}")
    public String packageAlreadyExist;

    @Value("${PACKAGE_NOT_FOUND}")
    public String packageNotFound;

    @Value("${PACKAGE_NOT_FOUND_WITH_ID}")
    public String packageNotFoundWithId;

    @Value("${PACKAGE_ADVERTISEMENT_COUNT_ZERO}")
    public String packageAdvertisementCountZero;

    @Value("${PACKAGE_VALID_DATETIME_FINISH}")
    public String packageValidDateTimeFinish;

}
