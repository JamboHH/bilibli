package com.six.pojo;

import lombok.Data;

import java.util.Date;

/**
 * @author ah
 */
@Data
public class Video {
    private Integer id;
    private String vName;
    private String vTypeOne;
    private String vAuthor;
    private Long vVolume;
    private String vCover;
    private Date vCreateDate;
    private String vUrl;
    private String vDes;
}
