package com.ctd.springboot.common.core.utils.code;

import java.util.Random;

/**
 * CodeUtils
 *
 * @author chentudong
 * @date 2020/12/3 21:08
 * @since 1.0
 */
public class CodeUtils {

    /**
     * 自定义进制(0,1没有加入,容易与o,l混淆)
     */
    private static final char[] R = new char[]{'Q', 'W', 'E', '8', 'A', 'S', '2', 'D', 'Z', 'X', '9', 'C', '7', 'P', '5', 'K', '3', 'M', 'J', 'U', 'F', 'R', '4', 'V', 'Y', 'T', 'N', '6', 'B', 'G', 'H'};

    /**
     * (不能与自定义进制有重复)
     */
    private static final char B = 'I';

    /**
     * 进制长度
     */
    private static final int BIN_LEN = R.length;
    private static final int MAX = 8;

    /**
     * 序列最小长度
     */
    private static final int S = 6;

    /**
     * 根据ID生成六位随机码
     *
     * @param id ID
     * @return String
     */
    public static String toCode(long id) {
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        char[] buf = new char[32];
        int charPos = 32;

        while ((id / BIN_LEN) > 0) {
            int ind = (int) (id % BIN_LEN);
            buf[--charPos] = R[ind];
            id /= BIN_LEN;
        }
        buf[--charPos] = R[(int) (id % BIN_LEN)];
        String str = new String(buf, charPos, (32 - charPos));
        // 不够长度的自动随机补全
        if (str.length() < S) {
            StringBuilder sb = new StringBuilder();
            sb.append(B);
            Random rnd = new Random();
            for (int i = 1; i < S - str.length(); i++) {
                sb.append(R[rnd.nextInt(BIN_LEN)]);
            }
            str += sb.toString();
        }
        return str;
    }

    /**
     * 获取6 位推广码
     *
     * @return String
     */
    public static String toCode() {
        String promoteCode = toCode(System.currentTimeMillis());
        int length = promoteCode.length();
        if (length > MAX) {
            return promoteCode.substring(length - 6, length);
        }
        return promoteCode;
    }

    /**
     * codeToId
     *
     * @param code code
     * @return long
     */
    public static long codeToId(String code) {
        char[] chs = code.toCharArray();
        long res = 0L;
        for (int i = 0; i < chs.length; i++) {
            int ind = 0;
            for (int j = 0; j < BIN_LEN; j++) {
                if (chs[i] == R[j]) {
                    ind = j;
                    break;
                }
            }
            if (chs[i] == B) {
                break;
            }
            if (i > 0) {
                res = res * BIN_LEN + ind;
            } else {
                res = ind;
            }
        }
        return res;
    }
}
