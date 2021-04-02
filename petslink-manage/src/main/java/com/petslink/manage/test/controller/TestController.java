package com.petslink.manage.test.controller;


import com.petslink.common.entity.userinfo.User;
import com.petslink.manage.test.service.TestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public String selectUser(
            @ApiParam(value = "用户ID", required = true) @RequestBody(required = true) User user) {
        testService.selectUser();
        return "查询成功了";
    }









}
