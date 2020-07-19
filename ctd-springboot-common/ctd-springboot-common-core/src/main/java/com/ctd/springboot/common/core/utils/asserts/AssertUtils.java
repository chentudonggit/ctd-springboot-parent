package com.ctd.springboot.common.core.utils.asserts;

import com.ctd.springboot.common.core.exception.InternalException;
import com.ctd.springboot.common.core.exception.UnifiedException;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;

/**
 * 校验
 *
 * @author chentudong
 * @date 2020/3/7 10:46
 * @since 1.0
 */
public final class AssertUtils {
    private AssertUtils() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * 判空抛异常给开发者
     *
     * @param obj obj
     * @param msg msg
     */
    public static void isNull(Object obj, String msg, Object... args) {
        if (isNull(obj)) {
            msgDevelopment(msg, args);
        }
    }

    /**
     * isNullToUser
     *
     * @param obj obj
     * @param msg msg
     */
    public static void isNullToUser(Object obj, String msg, Object... args) {
        if (isNull(obj)) {
            msgUser(msg, args);
        }
    }

    /**
     * boolean isNull
     *
     * @param obj obj
     * @return boolean
     */
    public static boolean isNull(Object obj) {
        return !nonNull(obj);
    }

    /**
     * 判断是否为空 size = 0 视为空
     *
     * @param obj obj
     * @return boolean
     */
    public static boolean nonNull(Object obj) {
        if (Objects.nonNull(obj)) {
            if (obj instanceof String) {
                return StringUtils.isNotBlank(obj.toString());
            } else if (obj instanceof Collection) {
                return !((Collection) obj).isEmpty();
            } else if (obj instanceof Map) {
                return !((Map) obj).isEmpty();
            } else if (obj.getClass().isArray()) {
                return Array.getLength(obj) > 0;
            }
            return true;
        }
        return false;
    }

    /**
     * 为空， 抛出RuntimeException
     *
     * @param obj obj
     * @param e   e
     */
    public static void isNull(Object obj, RuntimeException e) {
        if (isNull(obj)) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 判断为空,返回 null
     *
     * @param obj obj
     * @param <T> <T>
     * @return T
     */
    public static <T> T isNullReturnNull(Object obj) {
        return isNull(obj) ? null : transform(obj);
    }

    /**
     * 判断为空， 返回 param
     *
     * @param obj   obj
     * @param param param
     * @param <T>   <T>
     * @return T
     */
    public static <T> T isNullReturnParam(Object obj, T param) {
        return isNull(obj) ? param : transform(obj);
    }

    /**
     * msgDevelopment
     *
     * @param msg msg
     */
    public static void msgDevelopment(String msg, Object... args) {
        throw new InternalException(msg(msg, args));
    }

    /**
     * msgUser
     *
     * @param msg msg
     */
    public static void msgUser(String msg, Object... args) {
        throw new UnifiedException(msg(msg, args));
    }

    /**
     * msg
     * %s	字符串类型	“喜欢请收藏”
     * %c	字符类型	‘m’
     * %b	布尔类型	true
     * %d	整数类型（十进制）	88
     * %x	整数类型（十六进制）	FF
     * %o	整数类型（八进制）	77
     * %f	浮点类型	8.888
     * %a	十六进制浮点类型	FF.35AE
     * %e	指数类型	9.38e+5
     * %g	通用浮点类型（f和e类型中较短的）	不举例(基本用不到)
     * %h	散列码	不举例(基本用不到)
     * %%	百分比类型	％(%特殊字符%%才能显示%)
     * %n	换行符	不举例(基本用不到)
     * %tx	日期与时间类型（x代表不同的日期与时间转换符)	不举例(基本用不到)
     *
     * @param msg msg
     * @return String
     */
    public static String msg(String msg, Object... args) {
        return StringUtils.isBlank(msg) ? "网络异常，请稍后重试！" : String.format(msg, args);
    }

    /**
     * 类型转换
     *
     * @param obj obj
     * @param <T> <T>
     * @return T
     */
    public static <T> T transform(Object obj) {
        return (T) obj;
    }
}
