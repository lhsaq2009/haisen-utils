package com.haisen.test.model;

import com.google.common.collect.Lists;

import com.haisen.common.Constant;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

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
        System.out.println(Student.get());
    }

    public static Student get() {
        int size = Constant.personNames().size();
        int i = new Random().nextInt(size);
        return Student.builder().id(i).name(Constant.personNames().get(i)).build();
    }

    public static List<Student> list() {
        int id = 1;
        return Lists.newArrayList(
                Student.builder().id(id++).name("java").build(),
                Student.builder().id(id++).name("php").build(),
                Student.builder().id(id++).name("c").build()
        );
    }

    public static List<Student> listRandom(int size) {

        if (size < 0) {
            return Collections.emptyList();
        }

        ArrayList<Student> result = Lists.newArrayListWithCapacity(size);

        int namesSize = Constant.personNames().size();
        for (int i = 0; i < size; i++) {
            int id = new Random().nextInt(namesSize);
            result.add(Student.builder()
                    .id(id)
                    .name(Constant.personNames().get(id))
                    .build());
        }

        return result;
    }
}
