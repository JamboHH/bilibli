package com.six.pojo.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private String pic;
    private Integer uid;
    private String username;
    private String password;
    private String email;
    private String tel;
    private String code;


}
