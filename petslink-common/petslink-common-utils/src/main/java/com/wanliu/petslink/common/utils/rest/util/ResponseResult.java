package com.wanliu.petslink.common.utils.rest.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Sunny
 * @create 2021/3/15 统一返回结果体
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseResult<T> {

    /**
     * 是否响应成功
     */
    private Boolean success;
    /**
     * 响应状态码
     */
    private String code;
    /**
     * 响应数据
     */
    private T data;
    /**
     * 错误信息
     */
    private String message;

}
