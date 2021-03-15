package com.six.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.JsonObject;
import com.six.client.UserClient;
import com.six.common.BaseRes;
import com.six.dao.CommentMapper;
import com.six.pojo.Comment;
import com.six.pojo.User;
import com.six.pojo.Video;
import com.six.pojo.vo.VoComment;
import com.six.service.CommentService;
import com.six.utils.CookieUtils;
import com.six.utils.JwtUtils;
import com.six.utils.RedisUtils;
import org.codehaus.groovy.antlr.SourceBuffer;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import sun.text.resources.ja.FormatData_ja;

import javax.annotation.PreDestroy;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private UserClient userClient;
    @Autowired
    private CookieUtils cookieUtils;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    RedisUtils redisUtils;
    @Autowired
    RedisTemplate redisTemplate;

    @Override
    public BaseRes findAllCommentByVid(Map map) {
        BaseRes baseRes = new BaseRes();
        Integer vid = (Integer) map.get("vid");
        Integer page = (Integer) map.get("page");
        Integer limit = (Integer) map.get("limit");
        String order = null;
        if (map.get("order") != null) {
            order = map.get("order").toString();
        }
        PageHelper.startPage(page, limit);
        List<Comment> commentList = commentMapper.findByVid(vid, order);
        PageInfo<Comment> pageInfo = new PageInfo<>(commentList);
        List<VoComment> voCommentList = new ArrayList<>();
        if (commentList == null) {
            baseRes.setCode(201);
            baseRes.setMsg("暂无评论");
            return baseRes;
        }
        for (Comment comment : commentList) {
            VoComment voComment = new VoComment();
            BeanUtils.copyProperties(comment, voComment);
            Integer uid = comment.getUid();
            System.out.println("uid = " + uid);
            Map userMap = new HashMap();
            userMap.put("uid", uid);
            BaseRes byUserId = userClient.findByuid(userMap);
            if (byUserId == null) {
                baseRes.setCode(202);
                baseRes.setMsg("查无此人");
                return baseRes;
            }
            User user = com.alibaba.fastjson.JSONObject.parseObject(JSONObject.toJSON(byUserId.getData()).toString(), User.class);
            voComment.setUserName(user.getUsername());
            voComment.setUserPic(user.getPic());
            voCommentList.add(voComment);
        }
        baseRes.setCode(200);
        baseRes.setMsg("成功");
        baseRes.setData(voCommentList);
        baseRes.setTotal(pageInfo.getTotal());
        return baseRes;
    }

    @Override
    public BaseRes addComment(Map map, HttpServletRequest request) {
        BaseRes baseRes = new BaseRes();
        Integer vid = (Integer) map.get("vid");
        String content = map.get("content").toString();
        Cookie[] cookies = request.getCookies();
        String token = cookieUtils.getToken(cookies);
        Map verify = jwtUtils.Verify(token);
        Integer userId = (Integer) verify.get("uid");
        if (userId == null) {
            baseRes.setCode(201);
            baseRes.setMsg("请登录");
            return baseRes;
        }
        Comment comment = new Comment();
        comment.setCommentTime(new Date());
        comment.setVid(vid);
        comment.setUid(userId);
        comment.setContent(content);
        comment.setCommentTime(new Date());
        System.out.println(comment);
        commentMapper.addComment(comment);
        baseRes.setMsg("添加成功");
        baseRes.setCode(200);
        return baseRes;
    }

    @Override
    public BaseRes deleteComment(Integer cid) {
        BaseRes baseRes = new BaseRes();
        commentMapper.deleteComment(cid);
        baseRes.setMsg("删除成功");
        baseRes.setCode(200);
        return baseRes;
    }
    @Override
    public BaseRes commentLikes(Map map) {
        BaseRes baseRes = new BaseRes();
        Integer cid = (Integer) map.get("cid");
        String like = map.get("like").toString();
        Double comment = redisUtils.ZScore("comment", cid.toString());
        if (!StringUtils.isEmpty(like)) {
            if (like.equals("true")) {
                redisUtils.ZSet("comment", cid.toString(), comment + 1);
                baseRes.setCode(200);
                baseRes.setMsg("点赞成功");
                return baseRes;
            }
            if (like.equals("false")) {
                if (comment > 0) {
                    redisUtils.ZSet("comment", cid.toString(), comment - 1);
                    baseRes.setMsg("取消点赞成功");
                    baseRes.setCode(200);
                    return baseRes;
                }
                baseRes.setCode(201);
                baseRes.setMsg("无法取消点赞");
                return baseRes;
            }
        }
        return baseRes;
    }
}
