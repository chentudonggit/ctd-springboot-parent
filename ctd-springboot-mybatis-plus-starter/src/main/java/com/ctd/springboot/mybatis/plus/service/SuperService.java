package com.ctd.springboot.mybatis.plus.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ctd.springboot.common.core.vo.search.SearchVO;
import com.ctd.springboot.common.lock.DistributedLock;

/**
 * SuperService
 *
 * @author chentudong
 * @date 2020/3/8 9:25
 * @since 1.0
 */
public interface SuperService<T> extends IService<T>
{

    /**
     * 幂等性新增记录
     *
     * @param entity       实体对象
     * @param lock         锁实例
     * @param lockKey      锁的key
     * @param countWrapper 判断是否存在的条件
     * @param msg          对象已存在提示信息
     * @return Boolean
     */
    Boolean saveIdempotent(T entity, DistributedLock lock, String lockKey, Wrapper<T> countWrapper, String msg);

    /**
     * 幂等性新增记录
     *
     * @param entity       实体对象
     * @param lock         锁实例
     * @param lockKey      锁的key
     * @param countWrapper 判断是否存在的条件
     * @return Boolean
     */
    Boolean saveIdempotent(T entity, DistributedLock lock, String lockKey, Wrapper<T> countWrapper);

    /**
     * 幂等性新增或更新记录
     *
     * @param entity       实体对象
     * @param lock         锁实例
     * @param lockKey      锁的key
     * @param countWrapper 判断是否存在的条件
     * @param msg          对象已存在提示信息
     * @return Boolean
     */
    Boolean saveOrUpdateIdempotent(T entity, DistributedLock lock, String lockKey, Wrapper<T> countWrapper, String msg);

    /**
     * 幂等性新增或更新记录
     *
     * @param entity       实体对象
     * @param lock         锁实例
     * @param lockKey      锁的key
     * @param countWrapper 判断是否存在的条件
     * @return Boolean
     */
    Boolean saveOrUpdateIdempotent(T entity, DistributedLock lock, String lockKey, Wrapper<T> countWrapper);

    /**
     * findAll
     *
     * @param search search
     * @return IPage<T>
     */
    IPage<T> findAll(SearchVO search);
}
