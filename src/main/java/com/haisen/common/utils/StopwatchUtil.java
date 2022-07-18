package com.haisen.common.utils;

import com.google.common.base.Stopwatch;
import jodd.util.ThreadUtil;
import org.joda.time.DateTime;

import java.util.concurrent.TimeUnit;

/**
 * <p>〈功能概述〉.
 *
 * @author haisen /20227/18
 */
public class StopwatchUtil {
    private final Stopwatch stopwatch;

    private StopwatchUtil(Stopwatch stopwatch) {
        this.stopwatch = stopwatch;
    }

    public static StopwatchUtil newWatch() {
        return new StopwatchUtil(Stopwatch.createStarted());
    }

    public void println(String str) {
        System.out.println(this.stopwatch.elapsed(TimeUnit.MILLISECONDS) + "：" + str);
    }

    public static void printDate(String str) {
        System.out.println(DateTime.now().toString("yyyy-MM-dd HH:mm:ss:SSS") + "：" + str);
    }

    public static void main(String[] args) {
        StopwatchUtil stopwatchUtil = StopwatchUtil.newWatch();
        ThreadUtil.sleep(1000);
        stopwatchUtil.println("1111");
        ThreadUtil.sleep(1000);
        stopwatchUtil.println("22222");
    }
}
