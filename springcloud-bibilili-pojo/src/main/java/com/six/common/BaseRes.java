package com.six.common;

import lombok.Data;

@Data
public class BaseRes {
    private String msg;
    private Integer code;
    private Object data;
    private Long total;
}
