package com.six.controller;

import com.six.common.BaseRes;
import com.six.service.VideoLabelService;
import com.six.service.VideoTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class VideoTypeController {
    @Autowired
    private VideoTypeService tagService;

    @Autowired
    private VideoLabelService videoLabelService;

    @PostMapping("/findVideoAllTag")
    public BaseRes findVideoAllTag(@RequestBody Map map) {
        return tagService.findVideoAllTag(map);
    }

    @PostMapping("/findVideoLabelByTag")
    public BaseRes findVideoLabelByTag(@RequestBody Map map) {
        return videoLabelService.findVideoLabelByTag(map);
    }
}
