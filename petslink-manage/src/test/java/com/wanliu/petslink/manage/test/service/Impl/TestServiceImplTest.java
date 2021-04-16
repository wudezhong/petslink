package com.wanliu.petslink.manage.test.service.Impl;


import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

/**
 * @author WuDezhong
 * @date 2020/10/26 10:32
 */
@SpringBootTest
@ActiveProfiles("testINT")
class TestServiceImplTest {

    //日志配置
    private static Logger log = LoggerFactory.getLogger(TestServiceImpl.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void selectUser() {
        log.info("测试Junit");
        //测试数据库连接
        String sql = "select * from user";
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql);
        System.out.println("查询获取用户数据的条数："+maps.size());
        //断言
        Assertions.assertEquals(2,maps.size());
    }




}