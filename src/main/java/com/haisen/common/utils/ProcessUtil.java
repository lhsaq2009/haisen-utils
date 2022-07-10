package com.haisen.common.utils;

import java.lang.management.ManagementFactory;

/**
 * 进程工具类
 *
 * @author haisen /20227/10
 */
public class ProcessUtil {

    /**
     * 获取进程 PID
     */
    public static String getPID() {
        return ManagementFactory.getRuntimeMXBean().getName().split("@")[0];
    }

    public static void main(String[] args) {
        System.out.println(getPID());
    }
}
