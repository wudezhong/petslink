package com.wanliu.petslink.common.utils.rest.util;

import com.wanliu.petslink.common.utils.rest.ResultCodeEnum;

/**
 * @author Sunny
 * @create 2021/3/15 Restful API 工具类
 */
public class ResponseResultUtil {

    /**
     * 通用返回成功（没有返回结果）
     * 
     * @param <T>
     * @return
     */
    public static <T> ResponseResult<T> success() {
        ResponseResult<T> responseResult = getResponseResult(ResultCodeEnum.SUCCESS);
        responseResult.setSuccess(Boolean.TRUE);
        return responseResult;
    }

    /**
     * 通用返回成功（有返回结果）
     * 
     * @param data
     * @param <T>
     * @return
     */
    public static <T> ResponseResult<T> success(T data) {
        ResponseResult<T> responseResult = getResponseResult(ResultCodeEnum.SUCCESS);
        responseResult.setSuccess(Boolean.TRUE);
        responseResult.setData(data);
        return responseResult;
    }

    /**
     * 通用返回失败
     * 
     * @param resultCode
     * @param <T>
     * @return
     */
    public static <T> ResponseResult<T> failure(ResultCodeEnum resultCode) {
        ResponseResult<T> responseResult = getResponseResult(resultCode);
        responseResult.setSuccess(Boolean.FALSE);
        return responseResult;
    }

    /**
     * 自定义返回结果
     * 
     * @param responseCode
     * @param responseMessage
     * @param data
     * @param <T>
     * @return
     */
    public static <T> ResponseResult<T> success(String responseCode, String responseMessage, T data) {
        ResponseResult<T> responseResult = new ResponseResult<>();
        if (responseCode.equals("200")) {
            responseResult.setSuccess(Boolean.TRUE);
        } else {
            responseResult.setSuccess(Boolean.FALSE);
        }
        responseResult.setCode(responseCode);
        responseResult.setMessage(responseMessage);
        responseResult.setData(data);
        return responseResult;
    }

    /**
     * 封装状态码结果
     * 
     * @param responseCode
     * @param <T>
     * @return
     */
    public static <T> ResponseResult<T> getResponseResult(ResultCodeEnum responseCode) {
        ResponseResult<T> responseResult = new ResponseResult();
        responseResult.setCode(responseCode.getCode());
        responseResult.setMessage(responseCode.getMessage());
        return responseResult;
    }

}
