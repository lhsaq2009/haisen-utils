package com.haisen.common.utils;

import org.apache.commons.io.FileUtils;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;

/**
 * <p>〈功能概述〉.
 * @author haisen /20224/24 
 */
public class ByteUtils {

    /**
     * 以可读的方式输出大小；
     *
     * <dependency>
     *     <groupId>commons-io</groupId>
     *     <artifactId>commons-io</artifactId>
     *     <version>2.8.0</version>
     *     <scope>compile</scope>
     * </dependency>
     *
     * 或者使用：FileUtils.byteCountToDisplaySize(array.length)
     */
    public static String humanReadableByteCountBin(long bytes) {
        long absB = bytes == Long.MIN_VALUE ? Long.MAX_VALUE : Math.abs(bytes);
        if (absB < 1024) {
            return bytes + " B";
        }
        long value = absB;
        CharacterIterator ci = new StringCharacterIterator("KMGTPE");
        for (int i = 40; i >= 0 && absB > 0xfffccccccccccccL >> i; i -= 10) {
            value >>= 10;
            ci.next();
        }
        value *= Long.signum(bytes);
        return String.format("%.1f %ciB", value / 1024.0, ci.current());
    }

    public static void main(String[] args) {
        byte[] array = new byte[700 * 1024 * 1024];
        // 以可读的方式输出大小
        System.out.println(FileUtils.byteCountToDisplaySize(array.length));     // 输出：700 MB
        System.out.println(humanReadableByteCountBin(array.length));            // 输出：700.0 MiB
    }
}
