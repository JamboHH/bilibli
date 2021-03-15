package com.six.controller;

import com.six.pojo.resp.ResultResp;
import com.six.pojo.vo.UserGift;
import com.six.service.UserGiftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @Author ZhouJinDong
 * @Date 2021/1/8
 */
@RestController
@RequestMapping("/gift")
public class LiveGiftController {

    @Autowired
    UserGiftService userGiftService;

    //根据普通用户的id查找用户的金豆子
    @RequestMapping("/findByUid")
    public ResultResp findByUid(@RequestBody Map map){
        return userGiftService.findByUid((Integer) map.get("id"));
    }

    @RequestMapping("/findRank")
    public ResultResp findRank(){
        return userGiftService.findRank();
    }

    @RequestMapping("/sendGift")
    public ResultResp sendGift(@RequestBody UserGift userGift, HttpServletRequest request){
        return userGiftService.sendGift(userGift,request);
    }
}
