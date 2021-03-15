package com.six.dao;

import com.six.pojo.Video;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface VideoMapper {
    List<Video> findAll(@Param("typeName") String typeName, @Param("order") String order);

    List<Video> extensionFind(Integer number);

    List<Video> findVideoByTag(Integer tagId);

    Video findVideoById(Integer id);

    List<Video> findAllDesc(String type);

    List<Video> findVideoAllTagByLikesDesc(Integer tagId);

    List<Video> findVideoAllTagByTimeDesc(Integer tagId);
}
