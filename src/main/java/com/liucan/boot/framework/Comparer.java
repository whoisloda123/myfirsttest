package com.liucan.boot.framework;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * 算法实现多段式版本号比较大小，语言不限，要求能提交运行输出正确结果，前者大返回1，相等返回0，小于返回-1，比如 v5.9.10与v5.10.10比较返回-1
 * <p>
 * ，时间20分钟以内。
 *
 * @author liucan
 * @date 4/19/21
 */
public class Comparer {


    public static void main(String[] args) {
        String vi = "v5.10.10.1.4";
        String v2 = "v5.10.10.1";
        System.out.println(compareVersion(vi, v2));
    }

    public static int compareVersion(int[] v1, int[] v2) {
        if (v1 == null && v2 == null) {
            return 0;
        }
        if (v1 != null && v2 == null) {
            return 1;
        }

        if (v1 == null && v2 != null) {
            return -1;
        }
        int v1Length = v1.length;
        int v2Length = v2.length;
        int i = -1;
        while (i < v1Length - 1 && i < v2Length - 1) {
            i++;
            if (i == v1Length - 1 && i < v2Length - 1) {
                return -1;
            }
            if (i < v1Length - 1 && i == v2Length - 1) {
                return 1;
            }
            int viByte = v1[i];
            int v2Byte = v2[i];
            if (viByte == v2Byte) {
                continue;
            }
            return viByte > v2Byte ? 1 : -1;
        }
        return 0;
    }

    /**
     * 多段式版本号比较大小，比如 v5.9.10与比较返回-1
     *
     * @param v1 第一个version
     * @param v2 第二version
     * @return 前者大返回1，相等返回0，小于返回-1
     */
    public static int compareVersion(String v1, String v2) {

        int[] bytesV1 = toBytes(v1);
        int[] bytesV2 = toBytes(v2);
        return compareVersion(bytesV1, bytesV2);
    }

    /**
     * 将版本号通过，分隔开转换为byte数组
     *
     * @param version 版本号：v5.9.10
     * @return byte数组
     */
    private static int[] toBytes(String version) {
        String[] split = version.split("\\.");
        int[] v1 = new int[split.length];
        for (int i = 0; i < split.length; i++) {
            v1[i] = split[i].hashCode();
        }
        return v1;
    }
}