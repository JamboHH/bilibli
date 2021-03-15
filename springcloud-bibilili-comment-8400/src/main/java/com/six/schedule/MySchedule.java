package com.six.schedule;

import com.six.dao.CommentMapper;
import com.six.pojo.resp.Score;
import com.six.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class MySchedule {
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private CommentMapper commentMapper;

    @Scheduled(cron = "0 0 23 * * ?")
    public void SendMail() {
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
/*            System.out.println("该条评论的id为" + cid);
            System.out.println("该条评论的点赞数为 = " + likes);*/
            commentMapper.updateLikes(cid, likes);
        }
    }
}
