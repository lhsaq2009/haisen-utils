package com.haisen.common.utils;

import com.google.common.collect.Lists;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>〈功能概述〉.
 * @author haisen /20207/28 
 */
public class ArraysUtil {

    public static List<String> trimStr(String[] arr) {

        if (arr == null || arr.length == 0) {
            return Lists.newArrayList();
        }

        return Arrays.stream(arr)
                .map(StringUtils::trim)
                .collect(Collectors.toList());
    }

}
