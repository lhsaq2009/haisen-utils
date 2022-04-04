package com.haisen.test.model;

import lombok.Builder;
import lombok.Data;

/**
 * <p>〈功能概述〉.
 * @author haisen /20224/4 
 */
@Data
@Builder
public class Person {
    private Integer id;
    private String name;

    public static void main(String[] args) {
        Person build = Person.builder().build();
        System.out.println(build);
    }
}
