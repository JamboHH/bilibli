package com.six.service;

import com.six.pojo.resp.ResultResp;

/**
 * @Author ZhouJinDong
 * @Date 2020/12/29
 */
public interface SearchService {


    ResultResp searchKey(String key, Integer page, Integer size);
}
