package com.wanliu.petslink.common.utils.rest.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Sunny
 * @create 2021/3/16 投保规则异常信息（自定义异常信息）
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InsureRuleException extends RuntimeException {

    /**
     * 错误码
     */
    protected String code;

    protected String msg;

    /**
     * 有参构造器，返回码在枚举类中，这里可以指定错误信息
     *
     * @param msg
     */
    public InsureRuleException(String msg) {
        super(msg);
    }

}
