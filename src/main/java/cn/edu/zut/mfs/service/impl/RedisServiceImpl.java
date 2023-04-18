package cn.edu.zut.mfs.service.impl;

import cn.edu.zut.mfs.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * redis操作Service的实现类
 */
@Slf4j
@Service
public class RedisServiceImpl implements RedisService {
    private final StringRedisTemplate stringRedisTemplate;
    HashOperations<String, String, RSocketRequester> hashOperations;

    public RedisServiceImpl(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public void set(String key, String value) {
        stringRedisTemplate.opsForValue().set(key, value);
    }

    @Autowired
    public void setHashOperations(HashOperations<String, String, RSocketRequester> hashOperations) {
        this.hashOperations = hashOperations;
    }

    @Override
    public void writeHash(String key, String userId, RSocketRequester requester) {
        hashOperations.put(key, userId, requester);
    }

    @Override
    public RSocketRequester loadHash(String key, String userId) {
        return (RSocketRequester) hashOperations.get(key, userId);
    }

    @Override
    public Map<String, RSocketRequester> entries(String key) {
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