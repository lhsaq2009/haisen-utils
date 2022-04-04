package com.haisen.test.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;

/**
 * <p>〈功能概述〉.
 * @author haisen /20224/4 
 */
@Data
@SuperBuilder
public class Person {
    private Integer id;
    private String name;
}
