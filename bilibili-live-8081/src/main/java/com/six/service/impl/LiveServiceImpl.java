package com.six.service.impl;


import com.six.dao.LiveRepository;
import com.six.pojo.resp.ResultResp;
import com.six.pojo.resp.User;
import com.six.pojo.vo.RoomMessage;
import com.six.service.LiveService;
import com.six.utils.JwtUtils;
import com.six.utils.MyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Optional;

/**
 * @Author ZhouJinDong
 * @Date 2021/1/4
 */

@Service
public class LiveServiceImpl implements LiveService {

    @Autowired
    LiveRepository liveRepository;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    MyUtils myUtils;

    //    @Value("${push.url}")
    private String pushUrl = "rtmp://8.133.185.94:1935/live/";

    @Override
    public ResultResp openLive(RoomMessage roomMessage, HttpServletRequest request) {
        ResultResp resultResp = new ResultResp();
        if (roomMessage.getId() != null) {
            RoomMessage roomMessage1 = liveRepository.saveAndFlush(roomMessage);
            if (roomMessage1 == null) {
                resultResp.setCode(2003);
                resultResp.setMessage("修改失败！！");
                return resultResp;
            }
            resultResp.setCode(200);
            resultResp.setMessage("修改成功！");
            resultResp.setData(roomMessage1);
            return resultResp;
        }
        System.out.println("roomMessage = " + roomMessage.getId());
        //从 token 获取用户id
//        Cookie[] cookies = request.getCookies();
//        String token = myUtils.getToken(cookies);
//        if (token == null){
//            resultResp.setCode(2003);
//            resultResp.setMessage("token为空");
//            return resultResp;
//        }
//        Map map = jwtUtils.Verify(token);
//        Integer uid = (Integer) map.get("id");
        String number = myUtils.getNumber();
        String code = myUtils.getUUID();

        roomMessage.setPushUrl(pushUrl);
        roomMessage.setLiveCode(code);
        roomMessage.setPullUrl(pushUrl + code);
        roomMessage.setRoomNumber(number);
        roomMessage.setUid(3);

        RoomMessage roomMessage1 = liveRepository.saveAndFlush(roomMessage);
        if (roomMessage1 == null) {
            resultResp.setCode(2005);
            resultResp.setMessage("数据库写入失败！");
            return resultResp;
        }
        resultResp.setCode(200);
        resultResp.setMessage("开通成功！！");
        resultResp.setData(roomMessage1);
        return resultResp;
    }

    @Override
    public ResultResp findRoomByUserId(HttpServletRequest request) {
        ResultResp resultResp = new ResultResp();
        Cookie[] cookies = request.getCookies();
        String token = myUtils.getToken(cookies);
        if (token == null) {
            resultResp.setCode(2003);
            resultResp.setMessage("token为空");
            return resultResp;
        }
        Map map = jwtUtils.Verify(token);
        Integer uid = (Integer) map.get("id");
        RoomMessage roomMessage = liveRepository.findByUid(uid);
        if (roomMessage == null) {
            resultResp.setCode(2004);
            resultResp.setMessage("没有开通直播功能！！");
            return resultResp;
        }
        resultResp.setCode(200);
        resultResp.setData(roomMessage);
        resultResp.setMessage("找到用户的直播空间！！");
        return resultResp;
    }

    @Override
    public ResultResp findByLimit(Integer page, Integer size) {
        PageRequest pageRequest = new PageRequest(page, size);
        Page<RoomMessage> all = liveRepository.findAll(pageRequest);
        ResultResp resultResp = new ResultResp();
        if (all == null) {
            resultResp.setCode(2003);
            resultResp.setMessage("分页查询失败！");
            return resultResp;
        }
        resultResp.setCode(200);
        resultResp.setMessage("分页查询成功！！");
        resultResp.setData(all.getContent());
        resultResp.setTotal(all.getTotalElements());
        return resultResp;
    }

    @Override
    public ResultResp findRoomById(Integer id) {
        ResultResp resultResp = new ResultResp();
        if (id == null || "".equals(id)) {
            resultResp.setCode(2003);
            resultResp.setMessage("直播间的房间号id为空");
            return resultResp;
        }
        Optional<RoomMessage> byId = liveRepository.findById(id);
        if (!byId.isPresent()) {
            resultResp.setCode(2004);
            resultResp.setMessage("没有查到");
            return resultResp;
        }
        resultResp.setCode(200);
        resultResp.setData(byId.get());
        resultResp.setMessage("查到该直播");
        return resultResp;
    }

    @Override
    public ResultResp findUserByToken(HttpServletRequest request) {
        ResultResp resultResp = new ResultResp();
        Cookie[] cookies = request.getCookies();
        String token = myUtils.getToken(cookies);
        if (token == null) {
            resultResp.setCode(2003);
            resultResp.setMessage("token为空");
            return resultResp;
        }
        Map map = jwtUtils.Verify(token);
        Integer uid = (Integer) map.get("id");
        String username = (String) map.get("username");
        User user = new User();
        user.setUid(uid);
        user.setUsername(username);

        resultResp.setCode(200);
        resultResp.setMessage("从token中获取用户信息成功！");
        resultResp.setData(user);
        return resultResp;
    }


}
