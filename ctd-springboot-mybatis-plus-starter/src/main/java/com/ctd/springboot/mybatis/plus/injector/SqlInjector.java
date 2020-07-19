package com.ctd.springboot.mybatis.plus.injector;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.AbstractSqlInjector;
import com.ctd.springboot.mybatis.plus.injector.delete.DeleteAll;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * SqlInjector
 *
 * @author chentudong
 * @date 2020/3/8 9:19
 * @since 1.0
 */
public class SqlInjector extends AbstractSqlInjector {
    @Override
    public List<AbstractMethod> getMethodList(Class<?> mapperClass) {
        return Stream.of(new DeleteAll()).collect(Collectors.toList());
    }
}
