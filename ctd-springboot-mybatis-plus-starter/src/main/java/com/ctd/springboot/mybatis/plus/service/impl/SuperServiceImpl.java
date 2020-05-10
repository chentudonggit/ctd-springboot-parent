package com.ctd.springboot.mybatis.plus.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.core.toolkit.ReflectionKit;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ctd.springboot.common.core.utils.asserts.AssertUtils;
import com.ctd.springboot.common.core.vo.search.SearchVO;
import com.ctd.springboot.common.lock.DistributedLock;
import com.ctd.springboot.mybatis.plus.domain.base.BaseEntity;
import com.ctd.springboot.mybatis.plus.mapper.SuperMapper;
import com.ctd.springboot.mybatis.plus.service.SuperService;
import com.ctd.springboot.mybatis.plus.utils.condition.ConditionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;


/**
 * SuperServiceImpl
 *
 * @author chentudong
 * @date 2020/3/8 9:27
 * @since 1.0
 */
public class SuperServiceImpl<M extends SuperMapper<T>, T extends BaseEntity<T>> extends ServiceImpl<M, T> implements SuperService<T>
{
    @Autowired
    protected M mapper;

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
    @Override
    public Boolean saveIdempotent(T entity, DistributedLock lock, String lockKey, Wrapper<T> countWrapper, String msg)
    {
        AssertUtils.isNull(lock, "lock 不能为空");
        AssertUtils.isNull(lockKey, "lockKey 不能为空");
        try
        {
            //加锁
            if (lock.lock(lockKey))
            {
                //判断记录是否已存在
                int count = super.count(countWrapper);
                if (count == 0)
                {
                    return super.save(entity);
                } else
                {
                    if (StringUtils.isBlank(msg))
                    {
                        msg = entity.getTipsMsg().append("已存在").toString();
                    }
                    AssertUtils.msgDevelopment(msg);
                }
            } else
            {
                AssertUtils.msgDevelopment("锁等待超时");
            }
        } finally
        {
            lock.releaseLock(lockKey);
        }
        return true;
    }

    /**
     * 幂等性新增记录
     *
     * @param entity       实体对象
     * @param lock         锁实例
     * @param lockKey      锁的key
     * @param countWrapper 判断是否存在的条件
     * @return Boolean
     */
    @Override
    public Boolean saveIdempotent(T entity, DistributedLock lock, String lockKey, Wrapper<T> countWrapper)
    {
        return saveIdempotent(entity, lock, lockKey, countWrapper, null);
    }

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
    @Override
    public Boolean saveOrUpdateIdempotent(T entity, DistributedLock lock, String lockKey, Wrapper<T> countWrapper, String msg)
    {
        if (Objects.nonNull(entity))
        {
            Class<?> cls = entity.getClass();
            TableInfo tableInfo = TableInfoHelper.getTableInfo(cls);
            if (Objects.nonNull(tableInfo) && StringUtils.isNotBlank(tableInfo.getKeyProperty()))
            {
                Object idVal = ReflectionKit.getMethodValue(cls, entity, tableInfo.getKeyProperty());
                if (com.baomidou.mybatisplus.core.toolkit.StringUtils.checkValNull(idVal) || Objects.isNull(entity.selectById()))
                {
                    if (StringUtils.isBlank(msg))
                    {
                        msg = "已存在";
                    }
                    return this.saveIdempotent(entity, lock, lockKey, countWrapper, msg);
                } else
                {
                    return updateById(entity);
                }
            } else
            {
                AssertUtils.msgDevelopment("Error:  Can not execute. Could not find @TableId.");
            }
        }
        return false;
    }

    /**
     * 幂等性新增或更新记录
     *
     * @param entity       实体对象
     * @param lock         锁实例
     * @param lockKey      锁的key
     * @param countWrapper 判断是否存在的条件
     * @return Boolean
     */
    @Override
    public Boolean saveOrUpdateIdempotent(T entity, DistributedLock lock, String lockKey, Wrapper<T> countWrapper)
    {
        return this.saveOrUpdateIdempotent(entity, lock, lockKey, countWrapper, null);
    }

    /**
     * findAll
     *
     * @param search search
     * @return Page<T>
     */
    @Override
    public IPage<T> findAll(SearchVO search)
    {
        Integer page = AssertUtils.isNullReturnParam(search.getPage(), 0);
        Integer size = AssertUtils.isNullReturnParam(search.getSize(), 10);
        return page(new Page<>(page, size), ConditionUtils.getCondition(search));
    }
}
