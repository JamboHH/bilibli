package com.six.pojo.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
@Data
public class VoComment {
    private Integer cid;
    private Integer uid;
    private Integer vid;
    private String content;
    private int likes;
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:ss:mm")
    @JsonFormat(pattern = "yyyy-MM-dd hh:ss:mm")
    private Date commentTime;
    private String userName;
    private String userPic;
}
