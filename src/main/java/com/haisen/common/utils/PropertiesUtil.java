package com.haisen.common.utils;

import com.google.common.collect.Maps;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

/**
 * <p>〈功能概述〉.
 *
 * @author haisen /20228/4
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PropertiesUtil {
    private String filePath;

    @SneakyThrows
    public String readProp(String key) {
        InputStream in = this.getClass().getClassLoader().getResourceAsStream(filePath);
        Properties properties = new Properties();
        properties.load(in);
        return properties.getProperty(key);
    }

    @SneakyThrows
    public Map<String, Object> readAll() {
        InputStream in = PropertiesUtil.class.getClassLoader().getResourceAsStream(filePath);
        Properties properties = new Properties();
        properties.load(in);
        HashMap<String, Object> result = Maps.newHashMap();
        properties.stringPropertyNames().forEach(key -> result.put(key, properties.getProperty(key)));
        return result;
    }

    public static void main(String[] args) {
        PropertiesUtil util = new PropertiesUtil("system.properties");
        System.out.println(util.readProp("test"));
        System.out.println(util.readAll());
    }
}
