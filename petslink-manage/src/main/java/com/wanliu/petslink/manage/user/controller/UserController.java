package com.wanliu.petslink.manage.user.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wanliu.petslink.common.utils.rest.util.ResponseResult;
import com.wanliu.petslink.common.utils.rest.util.ResponseResultUtil;
import com.wanliu.petslink.manage.user.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author Sunny
 * @create 2021/4/15
 */
@RestController
@RequestMapping("/petslink-manage/user")
@Api(value = "UserController", tags = { "用户相关接口" })
public class UserController {

    // 引入公共资源
    @Autowired
    private UserService userService;

    @ApiOperation(value = "验证码", notes = "登录及鉴权")
    @GetMapping("/getVaildCode")
    public ResponseResult<Map<String, String>> vaildCode() {
        Map<String, String> map = userService.getVaildCode();
        return ResponseResultUtil.success(map);
    }

}
