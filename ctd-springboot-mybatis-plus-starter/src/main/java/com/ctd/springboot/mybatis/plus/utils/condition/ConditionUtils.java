package com.ctd.springboot.mybatis.plus.utils.condition;

import com.baomidou.mybatisplus.enums.SqlLike;
import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.toolkit.StringUtils;
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

    public static void searchConditions(Map<String, Object> where, Condition condition)
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
                    condition.like(StringUtils.camelToUnderline(k.split(L_LIKE)[0]), String.valueOf(v), SqlLike.LEFT);
                } else if (ConditionUtils.isLoadCondition(R_LIKE, k, v))
                {
                    condition.like(StringUtils.camelToUnderline(k.split(R_LIKE)[0]), String.valueOf(v), SqlLike.RIGHT);
                } else if (ConditionUtils.isLoadCondition(LIKE, k, v))
                {
                    condition.like(StringUtils.camelToUnderline(k.split(LIKE)[0]), String.valueOf(v));
                } else if (ConditionUtils.isLoadCondition(IN, k, v))
                {
                    condition.in(StringUtils.camelToUnderline(k.split(IN)[0]), String.valueOf(v));
                } else if (ConditionUtils.isLoadCondition(ISNULL, k, v))
                {
                    String camel = StringUtils.camelToUnderline(k.split(ISNULL)[0]);
                    condition.andNew(camel + " IS NULL OR " + camel + " = '' ");
                } else if (ConditionUtils.isLoadCondition(IS_NOTNULL, k, v))
                {
                    condition.isNotNull(StringUtils.camelToUnderline(k.split(IS_NOTNULL)[0]));
                }
            });
        }
    }


    public static Condition getCondition(SearchVO search) {
        Condition condition = Condition.create();
        if (Objects.nonNull(search)) {
             searchConditions(search.getWhere(), condition);
             sortConditions(search.getOrderBy(), condition);
        }
        return condition;
    }

    public static void sortConditions(Map<String, String> orderBy, Condition condition) {
        if (orderBy != null && !orderBy.isEmpty()) {
            StringBuilder stringBuilder = new StringBuilder();
            orderBy.forEach((k, v) -> {
                if (k.trim().length() > 0 && v.trim().length() > 0) {
                    stringBuilder.append(StringUtils.camelToUnderline(k));
                    stringBuilder.append("asc".equals(v.toLowerCase()) ? " ASC , " : " DESC ,");
                }
            });
            String orderBySql = stringBuilder.toString().trim();
            condition.orderBy(orderBySql.substring(0, orderBySql.length() - 1));
        } else {
            condition.orderBy("updated_dt", false);
        }
    }

    public static Condition searchConditions(Map<String, Object> where)
    {
        Condition condition = Condition.create();
        searchConditions(where, condition);
        return condition;
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

