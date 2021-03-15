package com.six.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.six.common.BaseRes;
import com.six.dao.UserDao;
import com.six.pojo.User;
import com.six.service.UserService;
import com.six.utils.JwtUtils;
import com.six.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private String from;
    @Autowired
    UserDao userDao;
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    RedisUtils redisUitls;

    @Override
    public BaseRes sendMail(String email, HttpServletRequest request) {


        BaseRes baseRes = new BaseRes();
        if (email != null) {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setFrom(from);
            simpleMailMessage.setTo(email);
            simpleMailMessage.setSubject("[QQ邮箱]");
            Random random = new Random();
            StringBuffer code = new StringBuffer();
            for (int i = 0; i < 4; i++) {
                int i1 = random.nextInt(10);
                code.append(i1);
            }
            simpleMailMessage.setText(code.toString());
            javaMailSender.send(simpleMailMessage);
            //存入session中
//       HttpSession session=request.getSession();
//            System.out.println(session.getId()+"验证码");
//       session.setAttribute(email,code.toString());
//            System.out.println(session.getAttribute(email));
//            System.out.println(email);

            //存入到redis中
            redisUitls.set(email, code.toString());
            System.out.println(code.toString());
            baseRes.setMsg("success");
            baseRes.setCode(200);
            return baseRes;
        }
        baseRes.setCode(201);
        baseRes.setMsg("fail");
        return baseRes;
    }

    @Override
    public BaseRes register(User user, HttpServletRequest request) {
        String code = (String) redisUitls.get(user.getEmail());
        BaseRes baseRes = new BaseRes();
        if (user.getCode().equals(code) && code != null) {
            User byUsername = userDao.findByUsername(user.getUsername());
            if (byUsername == null) {
                userDao.saveAndFlush(user);
                baseRes.setMsg("用户注册成功");
                baseRes.setCode(200);
                return baseRes;
            } else {
                baseRes.setMsg("用户已被注册");
                baseRes.setCode(201);
                return baseRes;
            }
        } else {
            baseRes.setMsg("用户验证码");
            baseRes.setCode(202);
            return baseRes;
        }
    }

    @Override
    public BaseRes login(User user) {
        System.out.println("user = " + user);
        //声明返回BaseResp
        BaseRes baseRes = new BaseRes();
        if (user.getUsername() == null || user.getUsername().equals("") || user.getPassword() == null || user.getPassword().equals("")) {
            baseRes.setCode(201);
            baseRes.setMsg("信息输入不完整");
            return baseRes;
        }
        User byUsernameAndPassword = userDao.findByUsernameAndPassword(user.getUsername(), user.getPassword());
        if (byUsernameAndPassword == null) {
            baseRes.setCode(202);
            baseRes.setMsg("用户信息输入错误，请重新输入");
            return baseRes;
        }
        Map map = new HashMap();
        map.put("userId", byUsernameAndPassword.getUid());
        map.put("userName", byUsernameAndPassword.getUsername());
        String token = jwtUtils.token(map);
        baseRes.setCode(200);
        baseRes.setMsg("登录成功");
        baseRes.setData(token);
        return baseRes;
    }

    @Override
    public BaseRes findByuid(Integer uid) {
        User user = userDao.findByUid(uid);
        BaseRes baseRes = new BaseRes();
        if (user != null) {
            baseRes.setCode(200);
            baseRes.setMsg("查询成功");
            baseRes.setData(user);
            return baseRes;
        } else {
            baseRes.setMsg("查无此人");
            baseRes.setCode(201);
            return baseRes;
        }
    }

}
