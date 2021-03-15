package com.six.dao;

import com.six.pojo.VideoType;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface VideoTypeMapper {
    List<VideoType> findVideoAllTag(String typeName);
}
