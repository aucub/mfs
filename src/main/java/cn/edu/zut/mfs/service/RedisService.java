package cn.edu.zut.mfs.service;

import java.util.Map;
import java.util.Set;

/**
 * redis操作Service,
 * 对象和数组都以json形式进行存储
 */
public interface RedisService {
    /**
     * 存储数据
     */
    void set(String key, String value);

    /**
     * 获取数据
     */
    String get(String key);

    /**
     * 设置超期时间
     */
    boolean expire(String key, long expire);

    /**
     * 删除数据
     */
    void remove(String key);

    /**
     * 自增操作
     *
     * @param delta 自增步长
     */
    Long increment(String key, long delta);

    void writeHash(String key, String userId, Integer value);

    void incrementHash(String key, String userId);

    void reduceHash(String key, String userId);

    Integer loadHash(String key, String userId);

    Map<String, String> entries(String key);

    Boolean delete(String key, String userId);

    Boolean hasKey(String key, String userId);

    Set<String> keys(String key);

    Long size(String key);


}
