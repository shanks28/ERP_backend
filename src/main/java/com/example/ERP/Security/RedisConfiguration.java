package com.example.ERP.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfiguration {

    @Autowired
    private Environment env;

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        String redisHost = env.getProperty("spring.redis.host");
        String redisPortStr = env.getProperty("spring.redis.port");

        if (redisHost == null || redisPortStr == null) {
            throw new IllegalStateException("Redis host/port not configured via environment variables");
        }

        int redisPort = Integer.parseInt(redisPortStr);

        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration(redisHost, redisPort);
        // Do not set password unless you have enabled Redis AUTH in ElastiCache
        return new LettuceConnectionFactory(config);
    }

    @Bean
    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, String> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new StringRedisSerializer());
        template.afterPropertiesSet();
        return template;
    }
}