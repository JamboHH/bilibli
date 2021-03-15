package com.six.service;

import com.six.common.BaseRes;
import com.six.pojo.User;

import javax.servlet.http.HttpServletRequest;

public interface UserService {

    BaseRes sendMail(String email, HttpServletRequest request);

    BaseRes register(User user, HttpServletRequest request);

//    BaseResp login(User user,HttpServletRequest request);


    BaseRes login(User user);

    BaseRes findByuid(Integer uid);
}
