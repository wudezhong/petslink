package com.wanliu.petslink.manage.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.wanliu.petslink.common.entity.userinfo.User;
import com.wanliu.petslink.common.utils.rest.util.ResponseResult;
import com.wanliu.petslink.common.utils.rest.util.ResponseResultUtil;
import com.wanliu.petslink.manage.test.service.TestService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @author Sunny
 * @create 2020/10/14
 */
@RestController
@RequestMapping("/petslink-manage/test")
@Api(value = "TestController", tags = {"Test测试相关"})
public class TestController {

    @Autowired
    private TestService testService;

    @PostMapping("/selectUser")
    @ApiOperation(value = "用户查询",notes = "测试数据库连接和redis访问")
    public ResponseResult<String> selectUser(
            @ApiParam(value = "用户ID", required = true) @RequestBody(required = true) User user) {
        String result = testService.selectUser();
        return ResponseResultUtil.success(result);
    }

    @PostMapping("/testPublicReturn")
    @ApiOperation(value = "测试公共数据返回", notes = "测试公共数据返回")
    public ResponseResult<String> testPublicReturn(
            @ApiParam(value = "参数一", required = true) @RequestParam(required = true) String param1) {
        String result = testService.testPublicReturn();
        return ResponseResultUtil.success(result);
    }









}
