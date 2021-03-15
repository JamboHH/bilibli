package com.six.pojo.vo;

import lombok.Data;

/**
 * @Author ZhouJinDong
 * @Date 2021/1/8
 *
 * 用户礼物类，
 * 把房间号作为key，用zset存储用户送出礼物的排行榜
 * value：用户名，score：用户分数
 */
@Data
public class UserGift {

    private Integer id;

    //房间id
    private Integer roomId;

    //用户id
    private Integer uid;

    //用户金豆子
    private Integer money;



}
