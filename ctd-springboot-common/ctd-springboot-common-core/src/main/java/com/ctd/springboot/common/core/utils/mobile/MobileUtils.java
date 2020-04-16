package com.ctd.springboot.common.core.utils.mobile;


import com.ctd.springboot.common.core.utils.asserts.AssertUtils;

import java.util.Collection;
import java.util.Objects;

/**
 * MobileUtils
 *
 * @author chentudong
 * @date 2020/3/25 19:09
 * @since 1.0
 */
public final class MobileUtils
{
    private MobileUtils()
    {
        throw new IllegalStateException("Utility class");
    }

    public static final String REGEX_MOBILE;

    static
    {
        REGEX_MOBILE = "(^((13[0-9])|(14[5-8])|(15([0-3]|[5-9]))|(16[6])|(17[0|4|6|7|8])|(18[0-9])|(19[8-9]))\\d{8}$)|(^((170[0|5])|(174[0|1]))\\d{7}$)|(^(14[1|4])\\d{10}$)";
    }

    /**
     * 校验手机号
     *
     * @param mobiles mobiles
     */
    public static void assertMobile(Collection<String> mobiles)
    {
        if (Objects.isNull(mobiles) || mobiles.isEmpty())
        {
            AssertUtils.isNull(mobiles, "mobiles 不能为空");
        }
        for (String mobile : mobiles)
        {
            assertMobile(mobile);
        }
    }

    /**
     * 校验手机号
     *
     * @param mobile mobile
     */
    public static void assertMobile(String mobile)
    {
        AssertUtils.isNullToUser(mobile, "请输入手机号");
        if (mobile.length() < 11)
        {
            AssertUtils.msgUser("手机号 %s 不合法，手机号应为11位数。", mobile);
        } else
        {
            if (!(mobile.matches(REGEX_MOBILE)))
            {
                AssertUtils.msgUser("手机号 %s 不合法，请修改。", mobile);
            }
        }
    }
}
