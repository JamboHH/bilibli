package com.six.controller;

import com.six.pojo.resp.ResultResp;
import com.six.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author ZhouJinDong
 * @Date 2020/12/29
 */

@RestController
@RequestMapping("/search")
public class SearchController {

    @Autowired
    SearchService searchService;

    @RequestMapping("/skey")
    public ResultResp searchKey(@RequestParam("key")String key, @RequestParam("page")Integer page, @RequestParam("size")Integer size){
        return  searchService.searchKey(key,page,size);
    }

}
