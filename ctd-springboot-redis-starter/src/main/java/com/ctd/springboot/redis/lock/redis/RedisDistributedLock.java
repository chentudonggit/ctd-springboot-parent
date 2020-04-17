package com.ctd.springboot.redis.lock.redis;

import com.ctd.mall.framework.common.lock.abstracts.AbstractDistributedLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.connection.ReturnType;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.types.Expiration;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * RedisDistributedLock
 *
 * @author chentudong
 * @date 2020/3/7 21:37
 * @since 1.0
 */
public class RedisDistributedLock extends AbstractDistributedLock
{
    private static final Logger LOGGER = LoggerFactory.getLogger(RedisDistributedLock.class);

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private ThreadLocal<String> lockFlag = new ThreadLocal<>();

    private static final String UNLOCK_LUA;

    /*
     * 通过lua脚本释放锁,来达到释放锁的原子操作
     */
    static
    {
        UNLOCK_LUA = "if redis.call(\"get\",KEYS[1]) == ARGV[1] " +
                "then " +
                "    return redis.call(\"del\",KEYS[1]) " +
                "else " +
                "    return 0 " +
                "end ";
    }

    /**
     * 获取锁
     *
     * @param key         key
     * @param expire      获取锁超时时间
     * @param retryTimes  重试次数
     * @param sleepMillis 获取锁失败的重试间隔
     * @return Boolean
     */
    @Override
    public Boolean lock(String key, long expire, int retryTimes, long sleepMillis)
    {
        boolean result = setRedis(key, expire);
        // 如果获取锁失败，按照传入的重试次数进行重试
        while ((!result) && retryTimes-- > 0)
        {
            try
            {
                LOGGER.debug("get redisDistributeLock failed, retrying..." + retryTimes);
                Thread.sleep(sleepMillis);
            } catch (InterruptedException e)
            {
                LOGGER.warn("Interrupted!", e);
                Thread.currentThread().interrupt();
            }
            result = setRedis(key, expire);
        }
        return result;
    }

    /**
     * 释放锁
     * 释放锁的时候，有可能因为持锁之后方法执行时间大于锁的有效期，
     * 此时有可能已经被另外一个线程持有锁，所以不能直接删除
     * 使用lua脚本删除redis中匹配value的key，可以避免由于方法执行时间过长
     * 而redis锁自动过期失效的时候误删其他线程的锁
     * spring自带的执行脚本方法中，集群模式直接抛出不支持执行脚本的异常，
     * 所以只能拿到原redis的connection来执行脚本
     *
     * @param key key值
     * @return Boolean
     */
    @Override
    public Boolean releaseLock(String key)
    {
        try
        {
            return redisTemplate.execute((RedisCallback<Boolean>) connection -> {
                byte[] scriptByte = redisTemplate.getStringSerializer().serialize(UNLOCK_LUA);
                return connection.eval(scriptByte, ReturnType.BOOLEAN, 1
                        , redisTemplate.getStringSerializer().serialize(key)
                        , redisTemplate.getStringSerializer().serialize(lockFlag.get()));
            });
        } catch (Exception e)
        {
            LOGGER.error("release redisDistributeLock occured an exception", e);
        } finally
        {
            lockFlag.remove();
        }
        return false;
    }

    private boolean setRedis(final String key, final long expire)
    {
        try
        {
            return redisTemplate.execute((RedisCallback<Boolean>) connection -> {
                String uuid = UUID.randomUUID().toString();
                lockFlag.set(uuid);
                byte[] keyByte = redisTemplate.getStringSerializer().serialize(key);
                byte[] uuidByte = redisTemplate.getStringSerializer().serialize(uuid);
                boolean result = connection.set(keyByte, uuidByte, Expiration.from(expire, TimeUnit.MILLISECONDS), RedisStringCommands.SetOption.ifAbsent());
                return result;
            });
        } catch (Exception e)
        {
            LOGGER.error("set redisDistributeLock occured an exception", e);
        }
        return false;
    }
}
