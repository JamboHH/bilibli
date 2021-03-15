package com.six;

import com.six.common.BaseRes;
import com.six.service.VideoTypeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
@RunWith(SpringRunner.class)
public class VideoTest {
    @Autowired
    private VideoTypeService videoService;

    @Test
    public void testFindAll() {
        Map map = new HashMap<>();
        map.put("type", "动画");
        BaseRes allVideo = videoService.findVideoAllTag(map);
        System.out.println("allVideo = " + allVideo);
    }
}
