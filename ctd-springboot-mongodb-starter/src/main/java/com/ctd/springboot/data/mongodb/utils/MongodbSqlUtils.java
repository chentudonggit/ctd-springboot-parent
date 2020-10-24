package com.ctd.springboot.data.mongodb.utils;

import com.ctd.springboot.common.core.bean.BeanHelper;
import com.ctd.springboot.common.core.utils.asserts.AssertUtils;
import com.ctd.springboot.common.core.vo.page.PageVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * MongodbSqlUtils
 *
 * @author chentudong
 * @date 2020/10/23 21:49
 * @since 1.0
 */
public class MongodbSqlUtils {

    public static final String SERIAL_VERSION_UID = "serialVersionUID";

    /**
     * matching
     *
     * @param object object
     * @return ExampleMatcher
     */
    public static ExampleMatcher matching(Object object) {
        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                //创建匹配器，即如何使用查询条件
                //改变默认字符串匹配方式：模糊查询
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                //改变默认大小写忽略方式：忽略大小写
                .withIgnoreCase(true)
                //采用“包含匹配”的方式查询
                //忽略属性，不参与查询
                .withIgnorePaths("page", "size", "pageNumber", "pageSize", "_class");
        if (Objects.nonNull(object)) {
            Field[] fields = object.getClass().getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                try {
                    String fieldName = field.getName();
                    if (SERIAL_VERSION_UID.equalsIgnoreCase(fieldName)) {
                        continue;
                    }
                    Object fieldValue = field.get(object);
                    if (Objects.nonNull(fieldValue) && StringUtils.isNotBlank(fieldValue.toString())) {
                        exampleMatcher.withMatcher(fieldName, ExampleMatcher.GenericPropertyMatcher::exact);
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return exampleMatcher.withIgnoreNullValues();
    }

    /**
     * example
     *
     * @param object object
     * @param <T>    <T>
     * @return Example<T>
     */
    public static <T> Example<T> example(Object object) {
        return (Example<T>) Example.of(object, matching(object));
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
        if (Objects.nonNull(direction) && Objects.nonNull(properties)) {
            return PageRequest.of(page, size, Sort.by(direction, properties));
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
