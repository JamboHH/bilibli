package com.six.service.impl;

import com.six.common.BaseRes;
import com.six.dao.VideoLabelMapper;
import com.six.pojo.Slabel;
import com.six.service.VideoLabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class VideoLabelServiceImpl implements VideoLabelService {
    @Autowired
    private VideoLabelMapper videoLabelMapper;

    @Override
    public BaseRes findVideoLabelByTag(Map map) {
        Integer tId = (Integer) map.get("tId");
        List<Slabel> slabelList = videoLabelMapper.findVideoLabelByTag(tId);
        BaseRes baseRes = new BaseRes();
        if (slabelList == null) {
            baseRes.setCode(201);
            baseRes.setMsg("查询失败");
            return baseRes;
        }
        baseRes.setCode(200);
        baseRes.setMsg("查询成功");
        baseRes.setData(slabelList);
        return baseRes;
    }
}
