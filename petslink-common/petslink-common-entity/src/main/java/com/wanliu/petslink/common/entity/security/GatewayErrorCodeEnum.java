package com.wanliu.petslink.common.entity.security;

/**
 * @author Sunny
 * @create 2021/2/2
 */
public enum GatewayErrorCodeEnum {

    REPEAT_REQUEST_ERROR("101", "重复请求"), INPUT_MUST_NOT_NULL("102", "参数不可为空"), REQUEST_VERIFY_FAIL("103",
            "请求验签失败"), REQUEST_DECRYPT_FAIL("104", "解密密文失败"), DATA_EXPIRED("105", "更新数据已过期"), UN_KNOWN("106",
                    "未知错误"), MAXIMUM_LIMIT("107", "超过最大查询记录限制"), ENUM_NOT_EXIST("108", "无匹配的数据字典定义"), AUTH_ERROR("109",
                            "授权异常"), UNKNOW_METHOD_ERROR("110", "该接口不存在"), UNKNOW_APPKEY_ERROR("111",
                                    "非法的appkey"), SYSTEM_ERROR("112",
                                            "系统异常"), ILLEGAL_IP("113", "ip未加入白名单 "), OVERFLOW("114", "访问超过限量 ");

    private String code;
    private String msg;

    GatewayErrorCodeEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static GatewayErrorCodeEnum getFlag(String msg) {
        GatewayErrorCodeEnum[] var4;
        int var3 = (var4 = values()).length;

        for (int var2 = 0; var2 < var3; ++var2) {
            GatewayErrorCodeEnum an = var4[var2];
            if (msg != null && msg.equals(an.getCode())) {
                return an;
            }
        }

        return null;
    }

    public String getCode() {
        return this.code;
    }

    public String getMsg() {
        return this.msg;
    }

}