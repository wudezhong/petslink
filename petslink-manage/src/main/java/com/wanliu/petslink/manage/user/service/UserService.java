package com.wanliu.petslink.manage.user.service;

import java.util.Map;

/**
 * @author Sunny
 * @create 2021/4/15 用户相关处理类
 */
public interface UserService {

    /**
     * 获取验证码图片
     */
    Map<String, String> getVaildCode();

}
