package com.six.service;

import com.six.pojo.resp.ResultResp;
import com.six.pojo.vo.RoomMessage;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author ZhouJinDong
 * @Date 2021/1/4
 */
public interface LiveService {


    ResultResp openLive(RoomMessage roomMessage, HttpServletRequest request);

    ResultResp findRoomByUserId(HttpServletRequest request);

    ResultResp findByLimit(Integer page, Integer size);

    ResultResp findRoomById(Integer id);

    ResultResp findUserByToken(HttpServletRequest request);
}
