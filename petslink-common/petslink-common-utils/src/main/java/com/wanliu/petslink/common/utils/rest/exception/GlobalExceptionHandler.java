package com.wanliu.petslink.common.utils.rest.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.wanliu.petslink.common.utils.rest.ResultCodeEnum;
import com.wanliu.petslink.common.utils.rest.util.ResponseResult;
import com.wanliu.petslink.common.utils.rest.util.ResponseResultUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Sunny
 * @create 2021/3/16 统一异常处理器
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 异常捕获
     * 
     * @param e
     *            捕获的异常
     * @return 封装的返回对象
     **/
    @ExceptionHandler(Exception.class)
    public ResponseResult handlerException(Exception e) {
        ResultCodeEnum resultCodeEnum;
        // 自定义异常
        if (e instanceof BusinessException) {
            resultCodeEnum = ResultCodeEnum.BUSINESS_EXCEPTION;
            resultCodeEnum.setMessage(getConstraintViolationErrMsg(e));
            log.error("业务异常：{}", resultCodeEnum.getMessage());
        } else if (e instanceof SystemException) {
            resultCodeEnum = ResultCodeEnum.CODE_EXCEPTION;
            resultCodeEnum.setMessage(getConstraintViolationErrMsg(e));
            log.error("代码异常：{}", resultCodeEnum.getMessage());
        } else if (e instanceof InsureRuleException) {
            resultCodeEnum = ResultCodeEnum.INSURERULE_EXCEPTION;
            resultCodeEnum.setMessage(getConstraintViolationErrMsg(e));
            log.error("校验规则异常：{}", resultCodeEnum.getMessage());
        } else {
            // 其他异常，当我们定义了多个异常时，这里可以增加判断和记录，来提示对应的编码
            resultCodeEnum = ResultCodeEnum.SERVER_ERROR;
            resultCodeEnum.setMessage(e.getMessage());
            log.error("系统异常：{}", e.getMessage());
            e.printStackTrace();
        }
        return ResponseResultUtil.failure(resultCodeEnum);
    }

    /**
     * 获取错误信息
     * 
     * @param ex
     * @return
     */
    private String getConstraintViolationErrMsg(Exception ex) {
        // validTest1.id: id必须为正数
        // validTest1.id: id必须为正数, validTest1.name: 长度必须在有效范围内
        String message = ex.getMessage();
        try {
            int startIdx = message.indexOf(": ");
            if (startIdx < 0) {
                startIdx = 0;
            }
            int endIdx = message.indexOf(", ");
            if (endIdx < 0) {
                endIdx = message.length();
            }
            message = message.substring(startIdx, endIdx);
            return message;
        } catch (Throwable throwable) {
            log.info("ex caught", throwable);
            return message;
        }
    }
}
