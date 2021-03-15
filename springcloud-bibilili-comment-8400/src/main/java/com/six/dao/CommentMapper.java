package com.six.dao;

import com.six.pojo.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CommentMapper {
    List<Comment> findByVid(@Param("vid") Integer vid, @Param("order") String order);

    void addComment(Comment comment);

    void deleteComment(Integer cid);

    List<Comment> findAll();

    int updateLikes(@Param("cid") Integer cid, @Param("likes") Integer likes);
}
