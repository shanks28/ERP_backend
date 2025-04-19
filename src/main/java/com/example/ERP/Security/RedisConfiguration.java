package com.example.ERP.Security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

@Configuration
public class RedisConfiguration {

    @Autowired(required = false)
    private Dotenv dotenv;

    @Autowired
    private Environment env;

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        String redisHost = env.getProperty("spring.redis.host");
        String redisPortStr = env.getProperty("spring.redis.port");

        // Fallback to Dotenv if not found (for local/dev)
        if ((redisHost == null || redisPortStr == null) && dotenv != null) {
            redisHost = dotenv.get("SPRING_REDIS_HOST");
            redisPortStr = dotenv.get("SPRING_REDIS_PORT");
        }
        int redisPort = Integer.parseInt(redisPortStr);

        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration(redisHost, redisPort);
        return new LettuceConnectionFactory(config);
    }

    // ... unchanged redisTemplate bean ...
}