package com.wanliu.petslink.manage.test.service.Impl;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.wanliu.petslink.common.utils.rest.exception.BusinessException;
import com.wanliu.petslink.manage.test.service.TestService;
import com.wanliu.petslink.manage.utils.RedisUtils;

/**
 * @author Sunny
 * @create 2020/6/28
 */
@Service
public class TestServiceImpl implements TestService {

    // 日志配置
    private static Logger log = LoggerFactory.getLogger(TestServiceImpl.class);

    // 引入公共资源
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private RedisUtils redisUtils;

    @Override
    public String selectUser() {
        // 测试数据库连接
        String sql = "select * from fu_user limit 10";
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql);
        for (Map<String, Object> map : maps) {
            System.out.println(map);
            System.err.println(map.keySet());
        }
        // 测试Redis连接
        stringRedisTemplate.opsForValue().set("test", "100", 60 * 10, TimeUnit.SECONDS);
        redisUtils.append("a", "123");
        log.info("testRedis缓存问题！");
        if (1 == 1) {
            throw new BusinessException("test异常问题！");
        }
        return "123";
    }

    @Override
    public String testPublicReturn() {

        if (1 == 1) {
            throw new BusinessException("test异常问题！");
        }

        return "333";
    }

}
