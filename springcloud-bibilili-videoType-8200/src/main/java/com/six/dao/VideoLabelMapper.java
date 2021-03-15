package com.six.dao;

import com.six.pojo.Slabel;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface VideoLabelMapper {
    List<Slabel> findVideoLabelByTag(Integer tId);
}
