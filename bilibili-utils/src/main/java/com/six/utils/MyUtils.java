package com.six.utils;


import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import java.util.Random;
import java.util.UUID;

@Component
public class MyUtils {

    public String getToken(Cookie[] cookies){

        if (cookies == null || cookies.length == 0){
            return null;
        }
        String  token = null;
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("token")){
                token = cookie.getValue();
            }
        }
        return token;
    }

    /**
     * 获取六位的编号
     * @return
     */
    public String getNumber(){
        StringBuffer stringBuffer = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            int i1 = random.nextInt(10);
            stringBuffer.append(i1);
        }
        return stringBuffer.toString();
    }

    /**
     * 获取随机字符串
     * @return
     */
    public String getUUID(){
        UUID uuid = UUID.randomUUID();
        String s = uuid.toString();
        return s.replace("-", "");
    }


}
