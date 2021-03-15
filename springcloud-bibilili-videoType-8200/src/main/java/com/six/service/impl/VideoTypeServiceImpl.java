package com.six.service.impl;

import com.six.common.BaseRes;
import com.six.dao.VideoTypeMapper;
import com.six.pojo.VideoType;
import com.six.service.VideoTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class VideoTypeServiceImpl implements VideoTypeService {
    @Autowired
    private VideoTypeMapper tagMapper;

    @Override
    public BaseRes findVideoAllTag(Map map) {
        String typeName = null;
        if (map.get("type") != null) {
            typeName = map.get("type").toString();
        }
        List<VideoType> tagList = tagMapper.findVideoAllTag(typeName);
        BaseRes baseRes = new BaseRes();
        baseRes.setCode(200);
        baseRes.setMsg("查询成功");
        baseRes.setData(tagList);
        return baseRes;
    }
}
