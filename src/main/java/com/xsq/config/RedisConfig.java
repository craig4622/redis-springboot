package com.xsq.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

import java.time.Duration;

/**
 * @ClassName RedisConfig
 * @Description TODO redis配置类
 * @Author xsq
 * @Date 2020/1/20 16:24
 **/
@Data
@Configuration
@ConfigurationProperties(
        prefix = "config.redis",
        ignoreUnknownFields = true
)
public class RedisConfig {

    private String host;
    private int port;
    private int database;
    private String password;
    private int timeout;
    private boolean usePool;
    private boolean convertPipelineAndTxResults;
    private int maxIdle;
    private int maxActive;
    private int maxWait;
    private boolean testOnBorrow;

    /**
     * 自定义jedis连接池
     *
     * @return
     */
    @Bean
    public JedisPoolConfig jedisPoolConfig() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(maxIdle);
        jedisPoolConfig.setMaxTotal(maxActive);
        jedisPoolConfig.setTestOnBorrow(testOnBorrow);
        jedisPoolConfig.setMaxWaitMillis(maxWait);
        return jedisPoolConfig;
    }

    @Bean
    public RedisConnectionFactory jedisConnectionFactory(JedisPoolConfig jedisPoolConfig) {
        //RedisStandaloneConfiguration指redis的单机模式,此外还有主从模式,集群模式
        RedisStandaloneConfiguration standaloneConfig = new RedisStandaloneConfiguration();
        standaloneConfig.setHostName(host);
        standaloneConfig.setPort(port);
        standaloneConfig.setDatabase(database);
        standaloneConfig.setPassword(RedisPassword.of(password));

        JedisClientConfiguration.JedisClientConfigurationBuilder builder = JedisClientConfiguration.builder().connectTimeout(Duration.ofMillis(timeout));
        //usePool设置true和false来控制是否使用redis连接池
        if (usePool) {
            builder.usePooling().poolConfig(jedisPoolConfig);
        }
        JedisConnectionFactory connectionFactory = new JedisConnectionFactory(standaloneConfig, builder.build());
        //指定是否将流水线结果转换为期望的数据类型
        connectionFactory.setConvertPipelineAndTxResults(convertPipelineAndTxResults);
        return connectionFactory;
    }

    /**
     * 设置RedisTemplate的序列化方式,RedisTemplate默认序列化方式是JdkSerializationRedisSerializer不友好,可以改成json方式具体百度
     *
     * @param redisConnectionFactory
     * @return
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        //使用StringRedisSerializer来序列化和反序列化redis的key值
        template.setKeySerializer(stringRedisSerializer);
        // hash的key也采用String的序列化方式
        template.setHashKeySerializer(stringRedisSerializer);
        // value序列化方式采用jackson
        template.setValueSerializer(new JdkSerializationRedisSerializer());
        // hash的value序列化方式采用jackson
        template.setHashValueSerializer(new JdkSerializationRedisSerializer());
        template.afterPropertiesSet();
        return template;
    }

    /**
     * 设置StringRedisTemplate的序列化方式,我这里是因为公司老的spring项目无论是RedisTemplate还是StringRedisTemplate
     * 统一设置的key为stringRedisSerializer序列化,value为JdkSerializationRedisSerializer序列化,改成json形式到时候对项目迭代会影响线上的redis原来的数据,
     * 我们真实情况一般推荐用转为json的形式,JdkSerializationRedisSerializer序列化方式性能差,而且保存的对象还必须实现Serializable接口,
     * 而且在用类似redisclient工具类查看时,用JdkSerializationRedisSerializer序列的数据就是一堆字节数组看不懂,不利于排查问题
     *
     * @param redisConnectionFactory
     * @return
     */
    @Bean
    public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();
        stringRedisTemplate.setConnectionFactory(redisConnectionFactory);
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        //使用StringRedisSerializer来序列化和反序列化redis的key值
        stringRedisTemplate.setKeySerializer(stringRedisSerializer);
        // hash的key也采用String的序列化方式
        stringRedisTemplate.setHashKeySerializer(stringRedisSerializer);
        // value序列化方式采用jackson
        stringRedisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());
        // hash的value序列化方式采用jackson
        stringRedisTemplate.setHashValueSerializer(new JdkSerializationRedisSerializer());
        stringRedisTemplate.afterPropertiesSet();
        return stringRedisTemplate;
    }
}
