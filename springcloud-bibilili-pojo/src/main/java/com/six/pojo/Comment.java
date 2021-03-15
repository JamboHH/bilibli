package com.six.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    private Integer cid;
    private Integer uid;
    private Integer vid;
    private String content;
    private int likes;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:ss:mm")
    @JsonFormat(pattern = "yyyy-MM-dd HH:ss:mm")
    private Date commentTime;
}
