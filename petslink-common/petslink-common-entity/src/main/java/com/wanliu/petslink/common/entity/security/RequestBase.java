package com.wanliu.petslink.common.entity.security;

import java.io.Serializable;

import lombok.Data;

/**
 * @author Sunny
 * @create 2021/2/2
 */
@Data
public class RequestBase implements Serializable {

    private static final long serialVersionUID = 1L;

    // 密钥
    private String appKey;

    // 请求服务名称
    private String serviceName;

    // 请求参数
    private Object bizContent;

    // 时间戳
    private String timestamp;

    // 请求版本号
    private String version;

    // 数据加密类型--暂时用不到
    private String encryptType;

    // 签文
    private String signValue;

}
