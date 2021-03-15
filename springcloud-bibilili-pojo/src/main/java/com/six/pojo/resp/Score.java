package com.six.pojo.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author ZhouJinDong
 * @Date 2021/1/8
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Score {
    private String value;
    private Double score;
}
