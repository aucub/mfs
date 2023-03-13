package cn.edu.zut.mfs.service.impl;

import cn.edu.zut.mfs.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * redis操作Service的实现类
 */
@Slf4j
@Service
public class RedisServiceImpl implements RedisService {
    private final StringRedisTemplate stringRedisTemplate;

    public RedisServiceImpl(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public void set(String key, String value) {
        stringRedisTemplate.opsForValue().set(key, value);
    }

    @Override
    public String get(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    @Override
    public boolean expire(String key, long expire) {
        return Boolean.TRUE.equals(stringRedisTemplate.expire(key, expire, TimeUnit.SECONDS));
    }

    @Override
    public void remove(String key) {
        stringRedisTemplate.delete(key);
    }

    @Override
    public Long increment(String key, long delta) {
        return stringRedisTemplate.opsForValue().increment(key, delta);
    }
}