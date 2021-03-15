package com.six;

import com.six.dao.CommentMapper;
import com.six.pojo.Comment;
import com.six.pojo.resp.Score;
import com.six.utils.RedisUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class RedisTest {
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    RedisUtils redisUtils;
    @Autowired
    RedisTemplate redisTemplate;

    @Test
    public void test() {
        List<Comment> all = commentMapper.findAll();
        for (Comment comment : all) {
            Integer cid = comment.getCid();
            int likes = comment.getLikes();
            redisUtils.ZSet("comment", cid.toString(), likes);
        }
    }

    @Test
    public void addLike() {
        Integer cid = 12;
        Double comment = redisUtils.ZScore("comment", cid.toString());
        redisUtils.ZSet("comment", cid.toString(), comment + 1);
    }

    @Test
    public void findAll() {
        List<Score> list = new ArrayList();
        Set<ZSetOperations.TypedTuple<String>> comment = redisUtils.ZRangeWithScore("comment", 0, -1);
        Iterator<ZSetOperations.TypedTuple<String>> iterator = comment.iterator();
        while (iterator.hasNext()) {
            ZSetOperations.TypedTuple<String> next = iterator.next();
            list.add(new Score(next.getValue(), next.getScore()));
        }
        Collections.reverse(list);
        System.out.println("list = " + list);
        for (Score score : list) {
            Integer likes = new Double(score.getScore()).intValue();
            Integer cid = Integer.parseInt(score.getValue());
            System.out.println("该条评论的id为" + cid);
            System.out.println("该条评论的点赞数为 = " + likes);
            commentMapper.updateLikes(cid, likes);
        }
    }
}
