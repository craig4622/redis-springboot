package com.xsq;


import com.xsq.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * @ClassName RedisUtilsTest
 * @Description TODO
 * @Author xsq
 * @Date 2020/1/20 16:08
 **/
@SpringBootTest
public class RedisUtilsTest {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void test1() {
        User user = new User();
        user.setName("无敌");
        user.setAge(11);
        redisTemplate.opsForValue().set("测试", user);
        System.out.println(redisTemplate.opsForValue().get("测试"));
    }

    @Test
    public void test2() {
        redisTemplate.opsForValue().set("xsq", "234", 8, TimeUnit.MINUTES);
        System.out.println(redisTemplate.opsForValue().get("xsq"));
    }

    @Test
    public void test3() {
        stringRedisTemplate.opsForValue().set("wudi", "234");
        System.out.println(stringRedisTemplate.opsForValue().get("wudi"));
    }

    @Test
    public void test4() {
        stringRedisTemplate.opsForValue().set("craig", "234", 6, TimeUnit.MINUTES);
        System.out.println(stringRedisTemplate.opsForValue().get("craig"));
    }
}
