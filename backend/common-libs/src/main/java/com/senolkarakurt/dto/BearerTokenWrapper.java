package com.senolkarakurt.dto;

import lombok.Getter;

@Getter
public class BearerTokenWrapper {

    public static String token = null;

    public static void setToken(String token) {
        BearerTokenWrapper.token = token;
    }
}
