package com.six.service;

import com.six.common.BaseRes;

import java.util.Map;

/**
 * @author ah
 */
public interface VideoService {
    BaseRes findAllVideo(Map map);
    BaseRes extensionFind(Integer num);
    BaseRes findVideoByTag(Map map);
    BaseRes findVideoById(Integer id);
    BaseRes findAllDesc(Map map);
    BaseRes findVideoAllTagByLikesDesc(Map map);
    BaseRes findVideoAllTagByTimeDesc(Map map);
}
