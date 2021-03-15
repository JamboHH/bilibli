package com.six.pojo.vo;


import lombok.Data;

import javax.persistence.*;

/**
 * @Author ZhouJinDong
 * @Date 2021/1/4
 */

@Data
@Entity
@Table(name = "room_message")
public class RoomMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 六位房间号
     */
    @Column(name = "room_number")
    private String roomNumber;

    /**
     * 房间标题
     */
    @Column(name = "room_title")
    private String roomTitle;

    /**
     * 直播类别
     */
    @Column(name = "room_type")
    private String roomType;

    /**
     * 推流地址
     * rtmp://124.71.207.196:1935/zb
     */
    @Column(name = "push_url")
    private String pushUrl;

    /**
     * 直播码
     */
    @Column(name = "live_code")
    private String liveCode;

    /**
     * 拉流地址
     * rtmp://124.71.207.196:1935/zb/直播码
     */
    @Column(name = "pull_url")
    private String pullUrl;

    private String pic;

    @Column(name = "uid")
    private Integer uid;

    @Column(name = "user_name")
    private String userName;



}
