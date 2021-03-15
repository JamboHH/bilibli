package com.six.utils;

import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;

/**
 * Created by 54110 on 2020/12/23.
 */
@Component
public class CookieUtils {
    public String getToken(Cookie[] cookies) {
        String token = "";
        if (cookies == null || cookies.length == 0) {
            return null;
        }
        for (Cookie cook : cookies
        ) {
            String name = cook.getName();
            if (name.equals("token")) {
                token = cook.getValue();
                return token;
            }
        }
        return null;
    }
}
