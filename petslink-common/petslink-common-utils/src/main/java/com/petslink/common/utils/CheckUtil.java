package com.petslink.common.utils;

/**
 * @author WuDezhong
 * @date 2019/11/27 14:24
 * 平台公共的校验规则
 */

import java.util.Date;

import org.springframework.util.StringUtils;

public class CheckUtil {

    //校验姓名
    public static boolean checkName(String name) {
        if (!name.matches("^[^·\\s]{1}[\\u2E80-\\uFE4Fa-zA-Z·\\s]{1,40}+$")) {
            return Boolean.FALSE;
        } else {
            if (name.contains(" ") && name.matches("^[\\u2E80-\\uFE4F\\s]{1,40}+$")) {
                return Boolean.FALSE;
            } else {
                if (name.substring(name.length() - 1, name.length()).equals(" ") || name.substring(name.length() - 1, name.length()).equals("·")) {
                    return Boolean.FALSE;
                }
            }
        }
        return Boolean.TRUE;
    }

}
