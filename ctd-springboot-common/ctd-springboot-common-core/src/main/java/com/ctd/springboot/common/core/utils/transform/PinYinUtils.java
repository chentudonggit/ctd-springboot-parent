package com.ctd.springboot.common.core.utils.transform;

import com.ctd.springboot.common.core.utils.asserts.AssertUtils;
import net.sourceforge.pinyin4j.PinyinHelper;
import org.apache.commons.lang3.StringUtils;

/**
 * 拼音工具类
 *
 * @author chentudong
 * @date 2020/10/17 17:23
 * @since 1.0
 */
public class PinYinUtils {
    private PinYinUtils() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * 获取汉字首字母的方法。如： 张三 --> ZS
     * 说明：暂时解决不了多音字的问题，只能使用取多音字的第一个音的方案
     *
     * @param param 汉子字符串
     * @return 大写汉子首字母; 如果都转换失败,那么返回param
     * 17
     */
    public static String getInitials(String param) {
        String result = param;
        if (StringUtils.isNotBlank(param)) {
            char[] charArray = param.toCharArray();
            StringBuilder sb = new StringBuilder();
            for (char ch : charArray) {
                // 逐个汉字进行转换， 每个汉字返回值为一个String数组（因为有多音字）
                String[] stringArray = PinyinHelper.toHanyuPinyinStringArray(ch);
                if (AssertUtils.nonNull(stringArray)) {
                    sb.append(stringArray[0].charAt(0));
                }
            }
            if (sb.length() > 0) {
                result = sb.toString().toUpperCase();
            }
        }
        return result;
    }

    /**
     * 获取汉字拼音的方法。如： 张三 --> zhangsan
     * 说明：暂时解决不了多音字的问题，只能使用取多音字的第一个音的方案
     *
     * @param param 汉子字符串
     * @return 汉字拼音; 如果都转换失败,那么返回 param
     */
    public static String getPinYin(String param) {
        String result = param;
        if (StringUtils.isNotBlank(param)) {
            char[] charArray = param.toCharArray();
            StringBuilder sb = new StringBuilder();
            for (char ch : charArray) {
                // 逐个汉字进行转换， 每个汉字返回值为一个String数组（因为有多音字）
                String[] stringArray = PinyinHelper.toHanyuPinyinStringArray(ch);
                if (AssertUtils.nonNull(stringArray)) {
                    // 把第几声这个数字给去掉
                    sb.append(stringArray[0].replaceAll("\\d", ""));
                }
            }
            if (sb.length() > 0) {
                result = sb.toString();
            }
        }
        return result;
    }
}
