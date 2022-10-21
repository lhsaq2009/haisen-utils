package com.haisen.common.utils;

/**
 * <p>〈功能概述〉.
 * @author haisen /202210/21 
 */
public class StringUtil {

    public static void main(String[] args) {
        System.out.println(getUnicode(" "));        // \u00a0
        System.out.println(getUnicode(" "));           // \u0020
    }

    /**
     * 输出字符串的 Unicode 编码
     */
    public static String getUnicode(String str) {
        if (str == null)
            return null;

        StringBuilder s = new StringBuilder();
        for (char c : str.toCharArray()) {
            s.append(c > 255 ? "\\u" : "\\u00")
                    .append(Integer.toHexString(c));
        }
        return s.toString();
    }
}
