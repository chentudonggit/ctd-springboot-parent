package com.ctd.springboot.common.core.bean;

import com.ctd.springboot.common.core.utils.asserts.AssertUtils;
import com.ctd.springboot.common.core.vo.page.PageVO;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.FatalBeanException;
import org.springframework.util.ClassUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * bean 转换
 *
 * @author chentudong
 * @date 2020/3/7 10:31
 * @since 1.0
 */
public class BeanHelper {
    private static DozerBeanMapper dozerBeanMapper;

    static {
        dozerBeanMapper = new DozerBeanMapper();
    }

    private BeanHelper() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * 实体转换
     *
     * @param s      源
     * @param tClass 目标
     * @param <T>    T
     * @param <S>    S
     * @return T
     */
    public static <T, S> T convert(S s, Class<T> tClass) {
        if (Objects.isNull(s)) {
            return null;
        }
        return dozerBeanMapper.map(s, tClass);
    }

    /**
     * Convert list.
     *
     * @param <T>     the type parameter
     * @param <S>     the type parameter
     * @param sources the sources
     * @param clazz   the clazz
     * @return the list
     */
    public static <T, S> List<T> convert(List<S> sources, Class<T> clazz) {
        List<T> result = new ArrayList<>();
        for (S s : sources) {
            if (!AssertUtils.isNull(s)) {
                result.add(dozerBeanMapper.map(s, clazz));
            }
        }
        return result;
    }

    /**
     * 获取参数
     *
     * @param map map
     * @param key key
     * @param <T> T
     * @return T
     */
    public static <T> T getParams(Map map, String key) {
        if (AssertUtils.isNull(map) || AssertUtils.isNull(key)) {
            return null;
        }
        return (T) map.get(key);
    }

    /**
     * convertToPageVO
     *
     * @param list      list
     * @param page      page
     * @param size      size
     * @param totalPage totalPage
     * @param total     total
     * @param <T>       <T>
     * @return PageVO<T>
     */
    public static <T> PageVO<T> convertToPageVO(List<T> list, Integer page, Integer size, Integer totalPage, Long total) {
        return convert(list, page, size, false, totalPage, total);
    }

    /**
     * 将集合转换为pageVO
     *
     * @param list list
     * @param page page
     * @param size size
     * @return PageVO
     */
    public static <T> PageVO<T> convertListToPageVO(List<T> list, Integer page, Integer size) {
        return convert(list, page, size, true, null, null);
    }

    /**
     * 初始化 PageVO
     *
     * @param source source
     * @param page   page
     * @param size   size
     * @param <T>    <T>
     * @return PageVO<T>
     */
    public static <T> PageVO<T> initPageVO(Class<T> source, Integer page, Integer size) {
        AssertUtils.isNull(source, "source 不能为空");
        size = AssertUtils.isNullReturnParam(size, 10);
        page = AssertUtils.isNullReturnParam(page, 0);
        PageVO<T> result = new PageVO<T>();
        result.setData(new ArrayList<T>());
        result.setTotalPage(0);
        result.setTotalCount(0L);
        result.setSize(size);
        result.setPage(page);
        return result;
    }

    /**
     * copyProperties
     *
     * @param source source
     * @param target target
     */
    public static void copyProperties(Object source, Object target) {
        AssertUtils.isNull(source, "Source must not be null");
        AssertUtils.isNull(target, "Target must not be null");
        Class<?> actualEditable = target.getClass();
        PropertyDescriptor[] targetPds = BeanUtils.getPropertyDescriptors(actualEditable);

        PropertyDescriptor[] descriptors = targetPds;
        for (int i = 0; i < targetPds.length; ++i) {
            PropertyDescriptor targetPd = descriptors[i];
            Method writeMethod = targetPd.getWriteMethod();
            if (Objects.nonNull(writeMethod)) {
                PropertyDescriptor sourcePd = BeanUtils.getPropertyDescriptor(source.getClass(), targetPd.getName());
                if (Objects.nonNull(sourcePd)) {
                    Method readMethod = sourcePd.getReadMethod();
                    if (Objects.nonNull(readMethod) && ClassUtils.isAssignable(writeMethod.getParameterTypes()[0], readMethod.getReturnType())) {
                        try {
                            if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
                                readMethod.setAccessible(true);
                            }

                            Object value = readMethod.invoke(source);
                            if (Objects.isNull(value)) {
                                continue;
                            }
                            if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
                                writeMethod.setAccessible(true);
                            }

                            writeMethod.invoke(target, value);
                        } catch (Throwable var15) {
                            throw new FatalBeanException("Could not copy property '" + targetPd.getName() + "' from source to target", var15);
                        }
                    }
                }
            }
        }
    }

    /**
     * convert
     *
     * @param list      list
     * @param page      page
     * @param size      size
     * @param isForEach isForEach
     * @param totalPage totalPage
     * @param total     total
     * @param <T>       <T>
     * @return PageVO<T>
     */
    private static <T> PageVO<T> convert(List<T> list, Integer page, Integer size, boolean isForEach, Integer totalPage, Long total) {
        page = AssertUtils.isNullReturnParam(page, 0);
        size = AssertUtils.isNullReturnParam(size, 10);
        PageVO<T> pageVO = new PageVO<>();
        if (Objects.isNull(list) || list.isEmpty()) {
            pageVO.setPage(size);
            pageVO.setTotalCount(0L);
            pageVO.setTotalPage(0);
            pageVO.setData(new ArrayList<>());
            return pageVO;
        }
        totalPage = Objects.nonNull(totalPage) ? totalPage : list.size();
        pageVO.setTotalPage((totalPage + size - 1) / size);
        pageVO.setSize(size);
        pageVO.setTotalCount(Objects.isNull(total) ? list.size() : total);
        if (isForEach) {
            List<T> resultList = new ArrayList<>();
            for (int i = page * size; i < (page + 1) * size; i++) {
                if (i < (list.size())) {
                    T t = list.get(i);
                    if (Objects.nonNull(t)) {
                        resultList.add(t);
                    }
                } else {
                    break;
                }
            }
            pageVO.setData(resultList);
        } else {
            pageVO.setData(list);
        }
        return pageVO;
    }
}
