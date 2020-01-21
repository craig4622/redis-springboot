package com.xsq.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @ClassName RedisUtil
 * @Description TODO
 * @Author xsq
 * @Date 2020/1/20 17:32
 **/
@Component
public class RedisUtil {


    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public void setValue(String key, String vlaue) {
        redisTemplate.opsForValue().set(key, vlaue);
    }

    public String getValue(String key) {
        return (String) redisTemplate.opsForValue().get(key);
    }

    public void setStringValue(String key, String vlaue) {
        stringRedisTemplate.opsForValue().set(key, vlaue);
    }

    public String getStringValue(String key) {
        return (String) stringRedisTemplate.opsForValue().get(key);
    }
}
