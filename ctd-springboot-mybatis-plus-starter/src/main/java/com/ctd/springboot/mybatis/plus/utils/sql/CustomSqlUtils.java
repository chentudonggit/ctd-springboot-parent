package com.ctd.springboot.mybatis.plus.utils.sql;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ctd.springboot.common.core.bean.BeanHelper;
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
        PageVO<T> result = new PageVO<>();
        result.setData(new ArrayList<>());
        int page = 0;
        int pages = 0;
        int size = 10;
        long total = 0;
        if (Objects.nonNull(s))
        {
            page = (int) s.getCurrent();
            size = (int) s.getSize();
            pages = (int) s.getPages();
            total = s.getTotal();
            List<S> records = s.getRecords();
            if (Objects.nonNull(records))
            {
                for (S record : records)
                {
                    result.getData().add(BeanHelper.convert(record, tClass));
                }
            }
        }
        result.setPage(page);
        result.setSize(size);
        result.setTotalPage(pages);
        result.setTotalCount(total);
        return result;
    }
}
