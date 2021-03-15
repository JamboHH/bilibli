package com.six.controller;

import com.six.common.BaseRes;
import com.six.service.ElSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class SearchController {
    @Autowired
    private ElSearchService elSearchService;

    /**
     * @param map page limit key
     * @return
     */
    @PostMapping("/selectKey")
    public BaseRes selectKey(@RequestBody Map map) {
        return elSearchService.selectKey(map);
    }
}
