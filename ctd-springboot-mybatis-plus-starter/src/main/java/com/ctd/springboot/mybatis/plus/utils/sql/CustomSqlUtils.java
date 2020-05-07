package com.ctd.springboot.mybatis.plus.utils.sql;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ctd.springboot.common.core.bean.BeanHelper;
import com.ctd.springboot.common.core.utils.asserts.AssertUtils;
import com.ctd.springboot.common.core.vo.page.PageVO;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * CustomSqlUtils
 *
 * @author chentudong
 * @date 2020/3/28 12:30
 * @since 1.0
 */
public class CustomSqlUtils
{
    /**
     * 实体转换
     *
     * @param s      源
     * @param tClass 目标
     * @param <T>    T
     * @param <S>    S
     */
    public static <T, S> PageVO<T> convert(IPage<S> s, Class<T> tClass)
    {
        long current = AssertUtils.isNullReturnParam(s.getCurrent(), 0L);
        long pageSize = AssertUtils.isNullReturnParam(s.getSize(), 10L);
        return convert(current, pageSize, s.getPages(), s.getTotal(), s.getRecords(), tClass);
    }

    public static <T, S> PageVO<T> convert(Page<S> s, Class<T> tClass)
    {
        return convert(s.getCurrent(), s.getSize(), s.getPages(), s.getTotal(), s.getRecords(), tClass);
    }

    /***
     * convert
     * @param current current
     * @param pageSize pageSize
     * @param pages pages
     * @param total total
     * @param records records
     * @param tClass tClass
     * @param <T> <T>
     * @param <S> <S>
     * @return PageVO<T>
     */
    public static <T, S> PageVO<T> convert(Long current, Long pageSize, Long pages, Long total, List<S> records, Class<T> tClass)
    {
        PageVO<T> result = new PageVO<>();
        result.setData(new ArrayList<>());
        if (Objects.nonNull(records))
        {
            for (S record : records)
            {
                result.getData().add(BeanHelper.convert(record, tClass));
            }
        }
        current = AssertUtils.isNullReturnParam(current, 0L);
        pageSize= AssertUtils.isNullReturnParam(pageSize, 10L);
        pages = AssertUtils.isNullReturnParam(pages, 0L);
        result.setPage(Integer.valueOf(current.toString()));
        result.setSize(Integer.valueOf(pageSize.toString()));
        result.setTotalPage(Integer.valueOf(pages.toString()));
        result.setTotalCount( AssertUtils.isNullReturnParam(total, 0L));
        return result;
    }
}
