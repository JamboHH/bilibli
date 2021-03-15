package com.six.controller;

import com.six.common.BaseRes;
import com.six.pojo.User;
import com.six.service.UserService;
import com.six.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RequestMapping("/user")
@RestController
@CrossOrigin
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    JwtUtils jwtUtils;
//    @Autowired
//    UpLoadUtils upLoadUtils;

    @PostMapping(value = "/login")
    public BaseRes login(@RequestBody User user) {
        return userService.login(user);
    }

    /**
     * @param map     email 发送的邮箱
     * @param request
     * @return
     */
    @RequestMapping(value = "/sendMail", method = RequestMethod.POST)
    public BaseRes sendMail(@RequestBody Map map, HttpServletRequest request) {
        return userService.sendMail(map.get("email").toString(), request);
    }

    @RequestMapping(value = "register", method = RequestMethod.POST)
    public BaseRes register(@RequestBody User user, HttpServletRequest request) {
        return userService.register(user, request);
    }

    /**
     * @param map 用户id
     * @return
     */
    @RequestMapping(value = "findByuid", method = RequestMethod.POST)
    public BaseRes findByuid(@RequestBody Map map) {
        System.out.println("map = " + map);
        return userService.findByuid((Integer) map.get("uid"));
    }
}
