package com.senolkarakurt.exception;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ExceptionSuccessCreatedMessage {
    public static final String CUSTOMER_CREATED = "müşteri oluşturuldu";
    public static final String ADVERTISEMENT_CREATED = "İlan oluşturuldu";
    public static final String PACKAGE_CREATED = "Paket oluşturuldu";
    public static final String USER_CREATED = "Kullanıcı oluşturuldu";
    public static final String ORDER_CREATED = "Sipariş verildi";
    public static final String INVOICE_CREATED = "Fatura oluşturuldu";
    public static final String LOGIN_SUCCESS = "Başarıyla giriş yapıldı";
}
