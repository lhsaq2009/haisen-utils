package com.haisen.test.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>〈功能概述〉.
 * @author haisen /20224/4 
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Student {

    private Integer id;
    private String name;

    public static void main(String[] args) {
        Student aa = new Student(1, "aa");
        System.out.println(aa);
    }
}
