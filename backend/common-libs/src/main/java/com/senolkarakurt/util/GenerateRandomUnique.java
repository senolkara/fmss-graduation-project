package com.senolkarakurt.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GenerateRandomUnique {
    public static String createRandomHash(String str){
        String generated = null;
        try
        {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            byte[] bytes = md.digest();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generated = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generated;
    }

    public static String createRandomOrderCode(){
        int max=9999999,min=1000000;
        return "ORD" + (min + (int)(Math.random() * ((max - min) + 1)));
    }

    public static String createRandomInvoiceNo(){
        int max=9999999,min=1000000;
        return "INV-" + (min + (int)(Math.random() * ((max - min) + 1)));
    }

    public static String createRandomAdvertisementNo(){
        int max=9999999,min=1000000;
        return String.valueOf((min + (int)(Math.random() * ((max - min) + 1))));
    }

}
