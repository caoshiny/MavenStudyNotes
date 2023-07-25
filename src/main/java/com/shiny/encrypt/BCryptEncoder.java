package com.shiny.encrypt;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BCryptEncoder {

    public static String bCryptEncode(String str){
        if(StringUtils.isBlank(str)){
            return null;
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(str);
    }


    public static boolean bCryptMatch(String str, String bCryptStr){
        if(StringUtils.isBlank(str)){
            return false;
        }
        if(StringUtils.isBlank(bCryptStr)){
            return false;
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.matches(str, bCryptStr);
    }

    public static void main(String[] args) {
        String encodeStr = bCryptEncode("$2a$10$9rtOQpVZHHEWWNtQZ8Q8O.09l4HBttKWDlp2HlLFxSd1x4V3ZCica");
        System.out.println(encodeStr);
        boolean match = bCryptMatch("$2a$10$9rtOQpVZHHEWWNtQZ8Q8O.09l4HBttKWDlp2HlLFxSd1x4V3ZCica", encodeStr);
        System.out.println(match);
//        match = bCryptMatch("123234322", encodStr);
//        System.out.println(match);
    }
}
