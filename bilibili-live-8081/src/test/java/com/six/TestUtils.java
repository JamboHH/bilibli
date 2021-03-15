package com.six;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.six.dao.LiveRepository;
import com.six.pojo.resp.Score;
import com.six.pojo.vo.RoomMessage;
import com.six.utils.RedisUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

/**
 * @Author ZhouJinDong
 * @Date 2021/1/4
 */

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestUtils {

    @Test
    public void getNumber(){
        StringBuffer stringBuffer = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            int i1 = random.nextInt(10);
            stringBuffer.append(i1);
        }
        System.err.println(stringBuffer);
    }

    @Test
    public void token(){
        //加密算法
        Algorithm algorithmHS = Algorithm.HMAC256("shop");
        //计算过期时间
        long l = System.currentTimeMillis();

        l+=60*1000*60*24*7;

        Date date = new Date(l);
        //自定义头部
        Map headMap = new HashMap<>();
        headMap.put("alg","HS256");
        headMap.put("typ","jwt");
        //签发人

        Map map = new HashMap();
        map.put("id",2);
        map.put("username","zhou");
        String sign = JWT.create().withHeader(headMap).
                withSubject("token").withIssuer("qf-live")
                .withClaim("body", map)
                .withIssuedAt(new Date())
                .withExpiresAt(date).sign(algorithmHS);
        System.out.println("sign = " + sign);

//        eyJ0eXAiOiJqd3QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0b2tlbiIsImlzcyI6InFmLWxpdmUiLCJib2R5Ijp7ImlkIjoyLCJ1c2VybmFtZSI6Inpob3UifSwiZXhwIjoxNjEwNTA0NDQxLCJpYXQiOjE2MDk4OTk2NDF9.ItJXRn6JAzJEP6P-R436iX2p50A89VUHfY0eaL6QjlY

    }

    @Autowired
    LiveRepository liveRepository;

    @Test
    public void testLimit(){
        PageRequest pageRequest = new  PageRequest(0, 5);
        Page<RoomMessage> all = liveRepository.findAll(pageRequest);
        System.out.println(all.getTotalElements());
        System.out.println(all.getContent());
    }

    @Autowired
    RedisUtils redisUtils;

    @Autowired
    RedisTemplate redisTemplate;

    @Test
    public void testRedis(){
//        redisUtils.ZSet("2323","zhou",23);
//        redisUtils.ZSet("2323","li",45);
//        redisUtils.ZSet("2323","zhang",23);
//        redisUtils.ZSet("2323","wnag",15);
//        redisUtils.ZSet("2323","lusen",34);
            //拿到value，从大到小，拿不到score
//        Set<String> strings = redisUtils.ZRevRange("2323", 0, -1);
//        System.out.println("strings = " + strings);


//        redisUtils.ZIncrScore("2323","zhang",100);


        List list = new ArrayList();
        Set<ZSetOperations.TypedTuple<String>> typedTuples = redisUtils.ZRangeWithScore("2323", 0, -1);
        Iterator<ZSetOperations.TypedTuple<String>> iterator = typedTuples.iterator();
        while (iterator.hasNext()){
            ZSetOperations.TypedTuple<String> next = iterator.next();
            list.add(new Score(next.getValue(),next.getScore()));
        }
        Collections.reverse(list);
        System.out.println("list = " + list);


    }
}


