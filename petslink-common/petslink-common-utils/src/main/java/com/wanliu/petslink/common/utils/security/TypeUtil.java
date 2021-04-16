package com.wanliu.petslink.common.utils.security;

import java.text.NumberFormat;

import com.alibaba.fastjson.JSONObject;

/**
 * @author Sunny
 * @create 2021/2/2
 */
public class TypeUtil {
    public TypeUtil() {
    }

    public static String obj2Str(Object obj) {
        String s = "";
        if (obj != null) {
            if (obj instanceof String) {
                s = (String) obj;
            } else {
                s = JSONObject.toJSONString(obj);
            }
        }

        return s;
    }

    public static String percent(int top, int bottom) {
        String baifenbi = "";
        double baiy = (double) top * 1.0D;
        double baiz = (double) bottom * 1.0D;
        double fen = baiy / baiz;
        NumberFormat nf = NumberFormat.getPercentInstance();
        nf.setMinimumFractionDigits(2);
        baifenbi = nf.format(fen);
        return baifenbi;
    }

    public static int divide(int top, int bottom) {
        double baiy = (double) top * 1.0D;
        double baiz = (double) bottom * 1.0D;
        double fen = baiy / baiz;
        return (int) Math.rint(fen);
    }
}