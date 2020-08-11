package com.ctd.springboot.feign.exception.utils;

import com.ctd.springboot.feign.exception.asserts.Then;
import org.springframework.lang.NonNull;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;

/**
 * ThenUtils
 * CustomConditionalAssertion
 *
 * @author chentudong
 * @date 2020/8/11 23:52
 * @since 1.0
 */
public class ThenUtils {
    /**
     * 设置全局默认异常 只需要设置一次，后面设置的会被把前面设置的覆盖掉
     *
     * @param defaultException defaultException
     */
    public static void setDefaultException(@NonNull Class<? extends RuntimeException> defaultException) {
        Then.setDefaultException(defaultException);
    }

    /**
     * 是否不为空 一个不为空则返回true
     *
     * @param param param
     * @return
     */
    public static Then isNotNull(Object... param) {
        boolean flag = false;
        for (Object o : param) {
            if (Objects.nonNull(o)) {
                flag = true;
                break;
            }
        }
        return new Then(flag);
    }

    /**
     * 是否不为空 一个为空则返回true
     *
     * @param params params
     * @return Then
     */
    public static Then isNull(Object... params) {
        boolean flag = false;
        for (Object o : params) {
            if (Objects.isNull(o)) {
                flag = true;
                break;
            }
        }
        return new Then(flag);
    }

    /**
     * 如果不相等则返回true
     *
     * @param param  param
     * @param source source
     * @return
     */
    public static Then isNotEqual(Object param, Object source) {
        return new Then(Objects.equals(param, source));
    }

    /**
     * 如果相等则返回true
     *
     * @param param  param
     * @param source source
     * @return Then
     */
    public static Then isEqual(Object param, Object source) {
        return new Then(!Objects.equals(param, source));
    }

    /**
     * 如果为true则返回true
     *
     * @param expression expression
     * @return Then
     */
    public static Then isTrue(boolean expression) {
        return new Then(expression);
    }

    /**
     * 如果为false则返回true
     *
     * @param expression expression
     * @return Then
     */
    public static Then isFalse(boolean expression) {
        return new Then(expression);
    }

    /**
     * 判断对象是否为空
     */
    public static <T> Then isEmpty(T param) {
        return new Then(isBlank(param));
    }

    /**
     * isBlank
     *
     * @param verifiedData verifiedData
     * @param <Input>      <Input>
     * @return <Input>
     */
    public static <Input> boolean isBlank(Input verifiedData) {
        if (verifiedData != null && verifiedData.getClass().isArray()) {
            return ((Input[]) verifiedData).length == 0;
        } else if (verifiedData instanceof Collection) {
            return ((Collection) verifiedData).isEmpty();
        } else if (verifiedData instanceof Map) {
            return ((Map) verifiedData).isEmpty();
        } else if (verifiedData instanceof CharSequence) {
            return ((CharSequence) verifiedData).length() == 0;
        } else {
            return verifiedData == null;
        }
    }
}
