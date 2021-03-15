package com.six.pojo.resp;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhoujindong
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultResp {

    /**
     * 状态码
     */
    private Integer code;

    /**
     * 数据
     */
    private Object data;

    /**
     * 提示信息
     */
    private  String message;

    /**
     * 分页的总数
     */
    private Long total;


}
