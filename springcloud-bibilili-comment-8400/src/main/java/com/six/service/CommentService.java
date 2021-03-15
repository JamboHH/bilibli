package com.six.service;

import com.six.common.BaseRes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface CommentService {
    BaseRes findAllCommentByVid(Map map);

    BaseRes addComment(Map map, HttpServletRequest request);

    BaseRes deleteComment(Integer cid);

    BaseRes commentLikes(Map map);
}
