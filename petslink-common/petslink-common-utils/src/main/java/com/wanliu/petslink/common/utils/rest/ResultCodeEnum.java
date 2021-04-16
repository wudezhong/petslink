package com.wanliu.petslink.common.utils.rest;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Sunny
 * @create 2021/3/16 响应结果集
 */
@Getter
@AllArgsConstructor
public enum ResultCodeEnum {

    /*** 通用部分 100 - 599 ***/
    // 成功请求
    SUCCESS("200", "成功"),
    // 重定向
    REDIRECT("301", "重定向异常"),
    // 资源未找到
    NOT_FOUND("404", "not found"),
    // 服务器错误
    SERVER_ERROR("500", "server error"),

    // 自定义业务异常
    BUSINESS_EXCEPTION("400", "业务异常"),

    // 自定义代码异常
    CODE_EXCEPTION("500", "代码异常"),

    // 自定义业务异常
    INSURERULE_EXCEPTION("300", "投保规则异常"),

    /*** 这里可以根据不同模块用不同的区级分开错误码，例如: ***/

    // 1000～1999 区间表示用户模块错误
    // 2000～2999 区间表示订单模块错误
    // 3000～3999 区间表示商品模块错误
    // 。。。

    ;
    /**
     * 响应状态码
     */
    private String code;
    /**
     * 响应信息
     */
    private String message;

    /**
     * 设置异常信息
     * 
     * @param message
     */
    public void setMessage(String message) {
        this.message = message;
    }

}
