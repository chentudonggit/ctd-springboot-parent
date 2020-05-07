package com.ctd.springboot.mybatis.plus.utils.condition;

import com.baomidou.mybatisplus.core.conditions.AbstractWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.ctd.springboot.common.core.utils.asserts.AssertUtils;
import com.ctd.springboot.common.core.vo.search.SearchVO;

import java.util.Map;
import java.util.Objects;

/**
 * ConditionUtils
 *
 * @author chentudong
 * @date 2020/5/4 2:12
 * @since 1.0
 */
public class ConditionUtils
{
    public static final String EQ = "_eq";
    public static final String NE = "_ne";
    public static final String LT = "_lt";
    public static final String LE = "_le";
    public static final String GT = "_gt";
    public static final String GE = "_ge";
    public static final String L_LIKE = "_llike";
    public static final String R_LIKE = "_rlike";
    public static final String LIKE = "_like";
    public static final String IN = "_in";
    public static final String ISNULL = "_isNull";
    public static final String IS_NOTNULL = "_isNotNull";

    public static void searchConditions(Map<String, Object> where, AbstractWrapper condition)
    {
        if (Objects.nonNull(where) && (!where.isEmpty()))
        {
            where.forEach((k, v) -> {
                if (ConditionUtils.isLoadCondition(EQ, k, v))
                {
                    condition.eq(StringUtils.camelToUnderline(k.split(EQ)[0]), v);
                } else if (ConditionUtils.isLoadCondition(NE, k, v))
                {
                    condition.ne(StringUtils.camelToUnderline(k.split(NE)[0]), v);
                } else if (ConditionUtils.isLoadCondition(LT, k, v))
                {
                    condition.lt(StringUtils.camelToUnderline(k.split(LT)[0]), v);
                } else if (ConditionUtils.isLoadCondition(LE, k, v))
                {
                    condition.le(StringUtils.camelToUnderline(k.split(LE)[0]), v);
                } else if (ConditionUtils.isLoadCondition(GT, k, v))
                {
                    condition.gt(StringUtils.camelToUnderline(k.split(GT)[0]), v);
                } else if (ConditionUtils.isLoadCondition(GE, k, v))
                {
                    condition.ge(StringUtils.camelToUnderline(k.split(GE)[0]), v);
                } else if (ConditionUtils.isLoadCondition(L_LIKE, k, v))
                {
                    condition.likeLeft(StringUtils.camelToUnderline(k.split(L_LIKE)[0]), String.valueOf(v));
                } else if (ConditionUtils.isLoadCondition(R_LIKE, k, v))
                {
                    condition.likeRight(StringUtils.camelToUnderline(k.split(R_LIKE)[0]), String.valueOf(v));
                } else if (ConditionUtils.isLoadCondition(LIKE, k, v))
                {
                    condition.like(StringUtils.camelToUnderline(k.split(LIKE)[0]), String.valueOf(v));
                } else if (ConditionUtils.isLoadCondition(IN, k, v))
                {
                    condition.in(StringUtils.camelToUnderline(k.split(IN)[0]), String.valueOf(v));
                } else if (ConditionUtils.isLoadCondition(ISNULL, k, v))
                {
                    String camel = StringUtils.camelToUnderline(k.split(ISNULL)[0]);
                    condition.comment(camel + " IS NULL OR " + camel + " = '' ");
                } else if (ConditionUtils.isLoadCondition(IS_NOTNULL, k, v))
                {
                    condition.isNotNull(StringUtils.camelToUnderline(k.split(IS_NOTNULL)[0]));
                }
            });
        }
    }

    public static AbstractWrapper getCondition(SearchVO search)
    {
        return getCondition(search, null);
    }

    public static AbstractWrapper getCondition(SearchVO search, AbstractWrapper wrapper)
    {
        wrapper = Objects.isNull(wrapper) ? new QueryWrapper<>() : wrapper;
        if (Objects.nonNull(search))
        {
            searchConditions(search.getWhere(), wrapper);
            sortConditions(search.getOrderBy(), wrapper);
        }
        return wrapper;
    }

    public static void sortConditions(Map<String, String> orderBy, AbstractWrapper wrapper)
    {
        if (AssertUtils.nonNull(orderBy))
        {
            orderBy.forEach((k, v) -> {
                if (k.trim().length() > 0 && v.trim().length() > 0)
                {
                    wrapper.orderBy(true, "asc".equals(v.toLowerCase()), StringUtils.camelToUnderline(k));
                }
            });
        } else
        {
            wrapper.orderByDesc("update_time");
        }
    }

    public static AbstractWrapper searchConditions(Map<String, Object> where)
    {
        AbstractWrapper wrapper = new QueryWrapper();
        searchConditions(where, wrapper);
        return wrapper;
    }

    public static boolean isLoadCondition(String conditionSuffix, String k, Object v)
    {
        if (ISNULL.equals(conditionSuffix) && k.endsWith(conditionSuffix))
        {
            return true;
        }
        return k.endsWith(conditionSuffix) && Objects.nonNull(v) && v.toString().trim().length() > 0;
    }
}

