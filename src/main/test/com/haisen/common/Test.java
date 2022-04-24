package com.haisen.common;

/**
 * <p>〈功能概述〉.
 * @author haisen /20224/14 
 */
public class Test {
    
    public static void main(String[] args) {
        new Test().test();
    }

    private void test() {
        System.out.println("test 1");

        try {
            int a = 1 / 0;

        } catch (Exception e) {

        }

        System.out.println("test 2");
    }
}
