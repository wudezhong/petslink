package com.wanliu.petslink.common.entity.security;

/**
 * @author Sunny
 * @create 2021/2/2
 */
public class GatewayException extends Exception {
    private static final long serialVersionUID = 1L;
    private String errorCode;
    private String attribute;

    public GatewayException(String errorCode, String msg, String attribute) {
        super(msg);
        this.setErrorCode(errorCode);
        this.setAttribute(attribute);
    }

    public GatewayException(String errorCode, String msg, Throwable cause) {
        super(msg, cause);
        this.setErrorCode(errorCode);
    }

    public GatewayException(String errorCode, String msg) {
        super(msg);
        this.setErrorCode(errorCode);
    }

    public String getErrorCode() {
        return this.errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getAttribute() {
        return this.attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }
}
