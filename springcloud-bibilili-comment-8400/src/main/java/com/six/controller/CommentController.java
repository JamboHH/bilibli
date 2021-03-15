package com.six.controller;

import com.six.common.BaseRes;
import com.six.pojo.Comment;
import com.six.pojo.vo.VoComment;
import com.six.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
public class CommentController {
    @Autowired
    private CommentService commentService;

    /**
     * @param map vid 视频id
     * @return
     */
    @PostMapping("/findAllCommetnByVid")
    public BaseRes findAllCommentByVid(@RequestBody Map map) {
        return commentService.findAllCommentByVid(map);
    }

    /**
     * @param map     vid视频id，content评论内容
     * @param request
     * @return
     */
    @PostMapping("/addComment")
    public BaseRes addComment(@RequestBody Map map, HttpServletRequest request) {
        return commentService.addComment(map, request);
    }

    /**
     * @param map cid 评论的id
     * @return
     */
    @PostMapping("/deleteComment")
    public BaseRes deleteComment(@RequestBody Map map){
        return commentService.deleteComment((Integer) map.get("cid"));
    }

    /**
     * @param map cid评论的id
     * @return
     */
    @PostMapping("/commentLikes")
    public BaseRes commentLikes(@RequestBody Map map){
        return commentService.commentLikes(map);
    }
}
