package com.six.service.impl;

import com.six.dao.UserGiftMapper;
import com.six.pojo.resp.ResultResp;
import com.six.pojo.resp.Score;
import com.six.pojo.vo.UserGift;
import com.six.service.UserGiftService;
import com.six.utils.JwtUtils;
import com.six.utils.MyUtils;
import com.six.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @Author ZhouJinDong
 * @Date 2021/1/8
 */
@Service
public class UserGiftServiceImpl implements UserGiftService {

    @Autowired
    UserGiftMapper userGiftMapper;

    @Autowired
    RedisUtils redisUtils;

    @Autowired
    MyUtils myUtils;

    private final String USER_SCORE = "user-score";

    @Override
    public ResultResp findByUid(Integer id) {
        ResultResp resultResp = new ResultResp();
        if (id == null){
            resultResp.setCode(2003);
            resultResp.setMessage("用户id为空！");
            return resultResp;
        }
        UserGift byUid = userGiftMapper.findByUid(id);
        if (byUid == null){
            resultResp.setCode(2004);
            resultResp.setMessage("没有查到该用户");
            return resultResp;
        }
        resultResp.setCode(200);
        resultResp.setMessage("成功查到该用户金豆子");
        resultResp.setData(byUid);
        return resultResp;
    }

    @Override
    public Integer updateByUid(UserGift userGift) {
        if (userGift.getUid() == null){
            return null;
        }
        Integer integer = userGiftMapper.updateByUid(userGift);
        if (integer == 1){
            return 1;
        }
        return null;
    }

    /**
     * 把房间号作为key，用zset存储用户送出礼物的排行榜
     * value：用户名，score：用户分数
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResultResp sendGift(UserGift userGift, HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        String token = myUtils.getToken(cookies);
        JwtUtils jwtUtils = new JwtUtils();
        Map verify = jwtUtils.Verify(token);
        String username = (String) verify.get("username");    //不用进行判断，在zuul服务器已经通过了

        ResultResp resultResp = new ResultResp();
        //计算用户增加的分数
        Integer money = userGift.getMoney();
        Integer score = money;
        UserGift byUid = userGiftMapper.findByUid(userGift.getUid());
        if (byUid == null){
            resultResp.setCode(2003);
            resultResp.setMessage("此用户没有开通送礼功能");
            return resultResp;
        }
        //修改用户的金额
        byUid.setMoney(byUid.getMoney()-money);
        updateByUid(byUid);
        //到redis增加用户的分数
        redisUtils.ZIncrScore(USER_SCORE,username,score);

        resultResp.setCode(200);
        resultResp.setMessage("发送成功！");
        resultResp.setData(byUid);
        return resultResp;
    }

    /**
     * 查找一个直播房间的用户排名
     * @return
     */
    @Override
    public ResultResp findRank() {
        ResultResp resultResp = new ResultResp();

        List list = new ArrayList();
        Set<ZSetOperations.TypedTuple<String>>  typedTuples = null;
        try {
            typedTuples = redisUtils.ZRangeWithScore(USER_SCORE, 0, -1);
        }catch (Exception e){
            e.printStackTrace();
            resultResp.setCode(2005);
            resultResp.setMessage("redis获取数据异常");
            return resultResp;
        }
        Iterator<ZSetOperations.TypedTuple<String>> iterator = typedTuples.iterator();
        while (iterator.hasNext()){
            ZSetOperations.TypedTuple<String> next = iterator.next();
            list.add(new Score(next.getValue(),next.getScore()));
        }
        Collections.reverse(list);
        resultResp.setCode(200);
        resultResp.setMessage("查找到用户排名");
        resultResp.setData(list);
        return resultResp;
    }
}
