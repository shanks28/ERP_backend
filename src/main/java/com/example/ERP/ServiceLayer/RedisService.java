package com.example.ERP.ServiceLayer;

import com.example.ERP.Security.RedisConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisService {
    private final RedisTemplate<Object,Object> redisTemplate;
    RedisService(RedisTemplate<Object,Object> redisTemplate){
        this.redisTemplate=redisTemplate;
    }
    public void set(Object key,Object value){
        redisTemplate.opsForValue().set(key,value);
    }
    public Object get(Object key){
        return redisTemplate.opsForValue().get(key);
    }
}
