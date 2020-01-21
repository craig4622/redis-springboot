package com.xsq.controller;

import com.xsq.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @ClassName RedisUtilsTest
 * @Description TODO
 * @Author xsq
 * @Date 2020/1/20 16:12
 **/
@RestController
public class RedisUtilsTest {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @RequestMapping("test1")
    public void test1(){
        redisUtil.setValue("123","234");
        System.out.println(redisUtil.getValue("123"));
    }

    @RequestMapping("test2")
    public void test2(){
        redisTemplate.opsForValue().set("xsq","234",8, TimeUnit.MINUTES);
        System.out.println(redisTemplate.opsForValue().get("xsq"));
    }

    @RequestMapping("test3")
    public void test3(){
        redisUtil.setStringValue("wudi","234");
        System.out.println(redisUtil.getStringValue("wudi"));
    }

    @RequestMapping("test4")
    public void test4(){
        stringRedisTemplate.opsForValue().set("craig","234",6, TimeUnit.MINUTES);
        System.out.println(stringRedisTemplate.opsForValue().get("craig"));
    }
}
