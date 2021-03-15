package com.six.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.six.common.BaseRes;
import com.six.dao.VideoMapper;
import com.six.pojo.Video;
import com.six.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class VideoServiceImpl implements VideoService {
    @Autowired
    private VideoMapper videoMapper;

    @Override
    public BaseRes findAllVideo(Map map) {
        Integer page = (Integer) map.get("page");
        Integer limit = (Integer) map.get("limit");
        String order = null;
        String type = null;
        if (map.get("type") != null) {
            type = map.get("type").toString();
            if (map.get("order") != null) {
                order = map.get("order").toString();
            }
        }
        PageHelper.startPage(page, limit);
        List<Video> videoList = videoMapper.findAll(type, order);
        PageInfo<Video> pageInfo = new PageInfo<>(videoList);
        BaseRes baseRes = new BaseRes();
        baseRes.setCode(200);
        baseRes.setData(pageInfo.getList());
        baseRes.setMsg("查询成功");
        baseRes.setTotal(pageInfo.getTotal());
        return baseRes;
    }

    @Override
    public BaseRes extensionFind(Integer num) {
        List<Video> videoList = videoMapper.extensionFind(num);
        BaseRes baseRes = new BaseRes();
        baseRes.setCode(200);
        baseRes.setData(videoList);
        baseRes.setMsg("查询成功");
        return baseRes;
    }

    @Override
    public BaseRes findVideoByTag(Map map) {
        Integer page = (Integer) map.get("page");
        Integer limit = (Integer) map.get("limit");
        Integer tagId = null;
        if (map.get("tagId") != null) {
            tagId = (Integer) map.get("tagId");
        }
        PageHelper.startPage(page, limit);
        List<Video> videoList = videoMapper.findVideoByTag(tagId);
        PageInfo<Video> pageInfo = new PageInfo<>(videoList);
        BaseRes baseRes = new BaseRes();
        baseRes.setCode(200);
        baseRes.setData(pageInfo.getList());
        baseRes.setMsg("查询成功");
        baseRes.setTotal(pageInfo.getTotal());
        return baseRes;
    }

    @Override
    public BaseRes findVideoById(Integer id) {
        BaseRes baseRes = new BaseRes();
        Video video = videoMapper.findVideoById(id);
        if (video == null) {
            baseRes.setCode(201);
            baseRes.setMsg("该视频已不存在");
            return baseRes;
        }
        baseRes.setMsg("查询成功");
        baseRes.setCode(200);
        baseRes.setData(video);
        return baseRes;
    }

    @Override
    public BaseRes findAllDesc(Map map) {
        Integer page = (Integer) map.get("page");
        Integer limit = (Integer) map.get("limit");
        String type = map.get("type").toString();
        BaseRes baseRes = new BaseRes();
        PageHelper.startPage(page, limit);
        List<Video> videoList = videoMapper.findAllDesc(type);
        PageInfo<Video> pageInfo = new PageInfo<>(videoList);
        if (videoList == null) {
            baseRes.setCode(201);
            baseRes.setMsg("失败");
            return baseRes;
        }
        baseRes.setCode(200);
        baseRes.setData(pageInfo.getList());
        return baseRes;
    }

    @Override
    public BaseRes findVideoAllTagByLikesDesc(Map map) {
        Integer page = (Integer) map.get("page");
        Integer limit = (Integer) map.get("limit");
        Integer tagId = null;
        if (map.get("tagId") != null) {
            tagId = (Integer) map.get("tagId");
        }
        PageHelper.startPage(page, limit);
        List<Video> videoList = videoMapper.findVideoAllTagByLikesDesc(tagId);
        PageInfo<Video> pageInfo = new PageInfo<>(videoList);
        BaseRes baseRes = new BaseRes();
        baseRes.setCode(200);
        baseRes.setData(pageInfo.getList());
        baseRes.setMsg("查询成功");
        baseRes.setTotal(pageInfo.getTotal());
        return baseRes;
    }

    @Override
    public BaseRes findVideoAllTagByTimeDesc(Map map) {
        Integer page = (Integer) map.get("page");
        Integer limit = (Integer) map.get("limit");
        Integer tagId = null;
        if (map.get("tagId") != null) {
            tagId = (Integer) map.get("tagId");
        }
        PageHelper.startPage(page, limit);
        List<Video> videoList = videoMapper.findVideoAllTagByTimeDesc(tagId);
        PageInfo<Video> pageInfo = new PageInfo<>(videoList);
        BaseRes baseRes = new BaseRes();
        baseRes.setCode(200);
        baseRes.setData(pageInfo.getList());
        baseRes.setMsg("查询成功");
        baseRes.setTotal(pageInfo.getTotal());
        return baseRes;
    }
}
