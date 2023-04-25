package cn.edu.zut.mfs.service.impl;

import cn.edu.zut.mfs.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * redis操作Service的实现类
 */
@Slf4j
@Service
public class RedisServiceImpl implements RedisService {
    private StringRedisTemplate stringRedisTemplate;
    private HashOperations<String, String, String> hashOperations;

    @Autowired
    public void setStringRedisTemplate(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
        this.hashOperations = stringRedisTemplate.opsForHash();
    }

    @Override
    public void set(String key, String value) {
        stringRedisTemplate.opsForValue().set(key, value);
    }

    @Override
    public void writeHash(String key, String userId, Integer value) {
        hashOperations.put(key, userId, String.valueOf(value));
    }

    @Override
    public void incrementHash(String key, String userId) {
        hashOperations.increment(key, userId, 1);
    }

    @Override
    public void reduceHash(String key, String userId) {
        hashOperations.increment(key, userId, -1);
    }

    @Override
    public Integer loadHash(String key, String userId) {
        return Integer.valueOf(Objects.requireNonNull(hashOperations.get(key, userId)));
    }

    @Override
    public Map<String, String> entries(String key) {
        return hashOperations.entries(key);
    }

    @Override
    public Boolean delete(String key, String userId) {
        return hashOperations.delete(key, userId) > 0;
    }

    @Override
    public Boolean hasKey(String key, String userId) {
        return hashOperations.hasKey(key, userId);
    }

    @Override
    public Set<String> keys(String key) {
        return hashOperations.keys(key);
    }

    @Override
    public Long size(String key) {
        return hashOperations.size(key);
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