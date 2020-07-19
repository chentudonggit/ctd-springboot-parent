package com.ctd.springboot.data.jpa.utils;

import com.ctd.springboot.common.core.bean.BeanHelper;
import com.ctd.springboot.common.core.utils.asserts.AssertUtils;
import com.ctd.springboot.common.core.vo.page.PageVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * JpaSqlUtils
 *
 * @author chentudong
 * @date 2020/3/28 12:28
 * @since 1.0
 */
public class JpaSqlUtils {
    public static final String CREATE_TIME = "createTime";
    public static final String UPDATE_TIME = "updateTime";

    /**
     * appendSqlLike
     *
     * @param obj obj
     * @return String
     */
    public static String appendSqlLike(Object obj) {
        if (Objects.nonNull(obj)) {
            return "%" + obj.toString().trim() + "%";
        }
        return "%%";
    }

    /**
     * 分页对象
     *
     * @param page       page
     * @param size       size
     * @param direction  direction
     * @param properties properties
     * @return Pageable
     */
    public static Pageable initPageable(Integer page, Integer size, Sort.Direction direction, String... properties) {
        page = AssertUtils.isNullReturnParam(page, 0);
        size = AssertUtils.isNullReturnParam(size, 10);
        size = Objects.isNull(size) ? 10 : size;
        if (Objects.nonNull(direction) && Objects.nonNull(properties)) {
            return PageRequest.of(page, size, new Sort(direction, properties));
        } else if (Objects.nonNull(direction)) {
            return PageRequest.of(page, size, Sort.by(direction));
        }
        return PageRequest.of(page, size);
    }

    /**
     * 实体转换
     *
     * @param s      源
     * @param tClass 目标
     * @param <T>    T
     * @param <S>    S
     */
    public static <T, S> PageVO<T> convert(Page<S> s, Class<T> tClass) {
        PageVO<T> result = new PageVO<>();
        result.setData(new ArrayList<>());
        int page = 0;
        int pages = 0;
        int size = 10;
        long total = 0;
        if (Objects.nonNull(s)) {
            size = s.getSize();
            pages = s.getTotalPages();
            total = s.getTotalElements();
            List<S> records = s.getContent();
            page = s.getPageable().getPageNumber();
            for (S record : records) {
                result.getData().add(BeanHelper.convert(record, tClass));
            }
        }
        result.setPage(page);
        result.setSize(size);
        result.setTotalPage(pages);
        result.setTotalCount(total);
        return result;
    }
}
