package com.wanliu.petslink.common.utils.security;

import com.alibaba.fastjson.JSONObject;
import com.wanliu.petslink.common.entity.security.GatewayErrorCodeEnum;
import com.wanliu.petslink.common.entity.security.GatewayException;
import com.wanliu.petslink.common.entity.security.RequestBase;
import com.wanliu.petslink.common.entity.security.ResponseBase;

/**
 * @author Sunny
 * @create 2021/2/2
 */
public class SecurityHelper {
    public SecurityHelper() {
    }

    public static void encryptAndSign(RequestBase requestBase, String publicKey, String type, String rsaPrivateKey)
            throws GatewayException {
        try {
            encrypt(requestBase, publicKey, type);
        } catch (Exception var6) {
            throw new GatewayException(GatewayErrorCodeEnum.UN_KNOWN.getCode(), "加密返回值失败");
        }

        try {
            sign(requestBase, type, rsaPrivateKey);
        } catch (Exception var5) {
            throw new GatewayException(GatewayErrorCodeEnum.UN_KNOWN.getCode(), "加签失败");
        }
    }

    public static String checkSignAndDecrypt(ResponseBase responseBase, String publicKey, String type,
            String rsaPrivateKey) throws GatewayException {
        boolean signFlag = false;

        try {
            signFlag = checkSign(responseBase, publicKey, type);
        } catch (Exception var8) {
            throw new GatewayException(GatewayErrorCodeEnum.REQUEST_VERIFY_FAIL.getCode(),
                    "签文有误,signValue:" + responseBase.getSignValue());
        }

        if (!signFlag) {
            throw new GatewayException(GatewayErrorCodeEnum.REQUEST_VERIFY_FAIL.getCode(),
                    "签文有误,signValue:" + responseBase.getSignValue());
        } else {
            String obj = null;

            try {
                obj = decrypt(responseBase.getBizContent(), publicKey, type, rsaPrivateKey);
                return obj;
            } catch (Exception var7) {
                throw new GatewayException(GatewayErrorCodeEnum.REQUEST_DECRYPT_FAIL.getCode(), "解密失败");
            }
        }
    }

    private static boolean checkSign(ResponseBase responseBase, String publicKey, String type) throws Exception {
        String content = SecurityUtil.getSortedStr(responseBase);
        String sign = responseBase.getSignValue();
        boolean isSuccess = false;
        if ("a".equals(type)) {
            isSuccess = SecurityUtil.rsaCheckSign(content, sign, publicKey);
        } else {
            isSuccess = SecurityUtil.md5CheckSign(content, sign);
        }

        return isSuccess;
    }

    private static void sign(RequestBase requestBase, String type, String rsaPrivateKey) throws Exception {
        String content = SecurityUtil.getSortedStr(requestBase);
        String sign = "";
        if ("a".equals(type)) {
            sign = SecurityUtil.rsaSign(content, rsaPrivateKey);
        } else {
            sign = SecurityUtil.md5Sign(content);
        }

        requestBase.setSignValue(sign);
    }

    private static void encrypt(RequestBase requestBase, String publicKey, String type) throws Exception {
        String content = TypeUtil.obj2Str(requestBase.getBizContent());
        String result;
        if ("a".equals(type)) {
            result = SecurityUtil.rsaEncrypt(content, publicKey);
            requestBase.setBizContent(result);
        } else if ("b".equals(type)) {
            result = SecurityUtil.desEncrypt(content, publicKey);
            requestBase.setBizContent(result);
        }

    }

    private static String decrypt(Object content, String publicKey, String type, String rsaPrivateKey)
            throws Exception {
        String decryptResult = "";
        if ("a".equals(type)) {
            decryptResult = SecurityUtil.rsaDecrypt((String) content, rsaPrivateKey);
        } else if ("b".equals(type)) {
            decryptResult = SecurityUtil.desDecrypt((String) content, publicKey);
        } else if (content instanceof String) {
            decryptResult = (String) content;
        } else {
            decryptResult = JSONObject.toJSONString(content);
        }

        return decryptResult;
    }

}
