package com.haisen.common.utils;

/**
 * <p>〈功能概述〉.
 *
 * @author haisen /20227/18
 */
public class ThreadUtil extends jodd.util.ThreadUtil {
    
    public static void printState(Thread t1) {
        System.out.println(t1.getState());
    }
}
