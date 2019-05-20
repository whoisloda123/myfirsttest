package com.liucan.boot.utils;

import redis.clients.jedis.Jedis;

import java.util.Collections;

/**
 * redis分布式锁
 * 相关资料https://www.cnblogs.com/linjiqin/p/8003838.html
 */
public class RedisDistributedLock {
    private static final String LOCK_SUCCESS = "OK";
    private static final String SET_IF_NOT_EXIST = "NX";
    private static final String SET_WITH_EXPIRE_TIME = "PX";
    private static final Long RELEASE_SUCCESS = 1L;

    /**
     * 尝试获取分布式锁
     *
     * @param jedis       Redis客户端
     * @param lockKey     锁
     * @param randomValue 请求随机值
     * @param expireTime  超期时间
     * @return 是否获取成功
     */
    public static boolean tryDistributedLock(Jedis jedis, String lockKey, String randomValue, int expireTime) {
        boolean bLock = false;
        try {
            String result = jedis.set(lockKey, randomValue, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, expireTime);
            if (result.equals(LOCK_SUCCESS)) {
                bLock = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bLock;
    }

    /**
     * 获取分布式锁
     *
     * @param jedis       Redis客户端
     * @param lockKey     锁
     * @param randomValue 请求随机值
     * @param expireTime  超期时间
     * @return 是否获取成功
     */
    public static void DistributedLock(Jedis jedis, String lockKey, String randomValue, int expireTime) {
        while (!RedisDistributedLock.tryDistributedLock(jedis, lockKey, randomValue, expireTime)) ;
    }

    /**
     * 释放分布式锁
     *
     * @param jedis       Redis客户端
     * @param lockKey     锁
     * @param randomValue 请求标识
     * @return 是否释放成功
     */
    public static boolean DistributedUnlock(Jedis jedis, String lockKey, String randomValue) {
        boolean bResult = false;
        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        Object result = jedis.eval(script, Collections.singletonList(lockKey), Collections.singletonList(randomValue));
        if (RELEASE_SUCCESS.equals(result)) {
            bResult = true;
        }
        return bResult;
    }
}
