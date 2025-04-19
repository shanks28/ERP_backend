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
import io.github.cdimascio.dotenv.Dotenv;

@Configuration
public class RedisConfiguration {

    @Autowired
    private Environment env;

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        // Try to get from Spring environment (system env, Docker Compose, application.properties)
        String redisHost = env.getProperty("spring.redis.host");
        String redisPortStr = env.getProperty("spring.redis.port");

        // Fallback to .env if not found (for local development)
        if ((redisHost == null || redisPortStr == null)) {
            try {
                Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
                if (redisHost == null) {
                    redisHost = dotenv.get("SPRING_REDIS_HOST");
                }
                if (redisPortStr == null) {
                    redisPortStr = dotenv.get("SPRING_REDIS_PORT");
                }
            } catch (Exception e) {
                // Ignore if .env is not present
            }
        }

        if (redisHost == null || redisPortStr == null) {
            throw new IllegalStateException("Redis host/port not configured via environment variables or .env file");
        }

        int redisPort = Integer.parseInt(redisPortStr);

        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration(redisHost, redisPort);
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