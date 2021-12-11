package com.haisen.common.utils;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * IP 地址工具类 Demo，未经过太多验证，仅限于 API 用法介绍
 *
 * 原文：https://blog.csdn.net/qq_33204709/article/details/80979685
 *
 */
public class AddressUtils {

    final static Pattern V4_IP_REG = Pattern.compile("\\<dd class\\=\"fz24\">(.*?)\\<\\/dd>");

    /**
     * 获取本机的内网 IP 地址
     */
    public static String getInnetIp() throws SocketException {
        AtomicReference<String> result = new AtomicReference<>();
        ArrayList<NetworkInterface> netInterfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
        netInterfaces.forEach(ni -> Collections.list(ni.getInetAddresses()).stream()
                .filter(ip -> !ip.isLoopbackAddress() && !ip.getHostAddress().contains(":"))
                .filter(InetAddress::isSiteLocalAddress)
                .forEach(ip -> result.set(ip.getHostAddress()))
        );
        return result.get();
    }

    /**
     * 获取本机的外网 IP 地址
     * 注意：速度较慢
     */
    public static String getV4IP() throws Exception {
        StringBuilder resp = new StringBuilder();
        URL url = new URL("http://ip.tool.chinaz.com/");
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(urlConnection.getInputStream(), StandardCharsets.UTF_8))
        ) {
            String read;
            while ((read = in.readLine()) != null) {
                resp.append(read).append("\r\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Matcher m = V4_IP_REG.matcher(resp.toString());
        if (m.find()) {
            return m.group(1);
        }
        return null;
    }

    public static void main(String[] args) throws Exception {
        System.out.println("内网 IP：" + AddressUtils.getInnetIp());
        System.out.println("外网 IP：" + AddressUtils.getV4IP());
    }
}