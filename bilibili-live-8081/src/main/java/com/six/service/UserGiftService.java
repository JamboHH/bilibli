package com.six.service;

import com.six.pojo.resp.ResultResp;
import com.six.pojo.vo.UserGift;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author ZhouJinDong
 * @Date 2021/1/8
 */

public interface UserGiftService {

    ResultResp findByUid(Integer id);

    Integer updateByUid(UserGift userGift);


    /**
     * 把房间号作为key，用zset存储用户送出礼物的排行榜
     * value：用户名，score：用户分数
     * @param userGift
     * @return
     */
    ResultResp sendGift(UserGift userGift, HttpServletRequest request);

    /**
     * 查找一个直播房间的用户排名
     * @return
     */
    ResultResp findRank();

}
