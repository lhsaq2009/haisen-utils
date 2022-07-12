package com.haisen.common.utils;


import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * <p>〈功能概述〉.
 *
 * @author haisen /20227/12
 */
public class ThreadPoolUtils {

    public static ExecutorService buildThreadPool(String threadName) {
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat(threadName + "-%d").build();
        return new ThreadPoolExecutor(
                5, 5,
                600L, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<>(100),            // 阻塞队列 => 推荐使用有界队列，有界队列有助于避免资源耗尽的情况发生
                namedThreadFactory,
                new ThreadPoolExecutor.AbortPolicy());
    }

}
