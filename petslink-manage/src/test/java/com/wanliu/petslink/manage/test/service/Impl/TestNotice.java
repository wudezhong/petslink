package com.wanliu.petslink.manage.test.service.Impl;

import org.junit.jupiter.api.*;

/**
 * @author Sunny
 * @create 2020/10/26
 */
@DisplayName("我的第一个测试用例")
public class TestNotice {

        @BeforeAll
        public static void init() {
            System.out.println("初始化数据");
        }

        @AfterAll
        public static void cleanup() {
            System.out.println("清理数据");
        }

        @BeforeEach
        public void tearup() {
            System.out.println("当前测试方法开始");
        }

        @AfterEach
        public void tearDown() {
            System.out.println("当前测试方法结束");
        }

        @DisplayName("我的第一个测试")
        @Test
        void testFirstTest() {
            System.out.println("我的第一个测试开始测试");
        }

        @DisplayName("我的第二个测试")
        @Test
        void testSecondTest() {
            System.out.println("我的第二个测试开始测试");
        }
}
