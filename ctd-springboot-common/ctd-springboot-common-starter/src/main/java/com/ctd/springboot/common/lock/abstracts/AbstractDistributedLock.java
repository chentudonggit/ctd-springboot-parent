package com.ctd.springboot.common.lock.abstracts;


import com.ctd.springboot.common.lock.DistributedLock;

/**
 * AbstractDistributedLock
 *
 * @author chentudong
 * @date 2020/3/7 21:34
 * @since 1.0
 */
public abstract class AbstractDistributedLock implements DistributedLock
{
    /**
     * 获取锁
     *
     * @param key key
     * @return Boolean
     */
    @Override
    public Boolean lock(String key)
    {
        return lock(key, TIMEOUT_MILLIS, RETRY_TIMES, SLEEP_MILLIS);
    }

    /**
     * 获取锁
     *
     * @param key        key
     * @param retryTimes 重试次数
     * @return Boolean
     */
    @Override
    public Boolean lock(String key, int retryTimes)
    {
        return lock(key, TIMEOUT_MILLIS, retryTimes, SLEEP_MILLIS);
    }

    /**
     * 获取锁
     *
     * @param key         key
     * @param retryTimes  重试次数
     * @param sleepMillis 获取锁失败的重试间隔
     * @return Boolean
     */
    @Override
    public Boolean lock(String key, int retryTimes, long sleepMillis)
    {
        return lock(key, TIMEOUT_MILLIS, retryTimes, sleepMillis);
    }

    /**
     * 获取锁
     *
     * @param key    key
     * @param expire 获取锁超时时间
     * @return Boolean
     */
    @Override
    public Boolean lock(String key, long expire)
    {
        return lock(key, expire, RETRY_TIMES, SLEEP_MILLIS);
    }

    /**
     * 获取锁
     *
     * @param key        key
     * @param expire     获取锁超时时间
     * @param retryTimes 重试次数
     * @return Boolean
     */
    @Override
    public Boolean lock(String key, long expire, int retryTimes)
    {
        return lock(key, expire, retryTimes, SLEEP_MILLIS);
    }
}
