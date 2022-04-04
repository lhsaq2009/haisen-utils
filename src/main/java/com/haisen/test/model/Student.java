package com.haisen.test.model;

import com.google.common.collect.Lists;

import java.util.List;

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

    public static Student get() {
        return Student.builder().id(1).name("java").build();
    }

    public static List<Student> list() {
        int id = 1;
        return Lists.newArrayList(
                Student.builder().id(id++).name("java").build(),
                Student.builder().id(id++).name("php").build(),
                Student.builder().id(id++).name("c").build()
        );
    }


}
