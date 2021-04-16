package com.wanliu.petslink.common.utils.security;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.*;
import java.lang.reflect.Method;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONObject;

/**
 * @author Sunny
 * @create 2021/2/2
 */
public class SecurityUtil {
    private static final String SIGN_TYPE_RSA = "RSA";
    private static final String CHARSET_UTF_8 = "UTF-8";
    private static final String ALGORITHM = "DESede";
    private static final String SIGN_ALGORITHMS = "SHA1WithRSA";
    private static final int MAX_ENCRYPT_BLOCK_1024 = 117;
    private static final int MAX_DECRYPT_BLOCK_1024 = 128;
    private static final int MAX_ENCRYPT_BLOCK_2048 = 245;
    private static final int MAX_DECRYPT_BLOCK_2048 = 256;
    private static final int MAX_ENCRYPT_LIMIT = 300;
    private static final int MAX_DECRYPT_LIMIT = 1000;

    public SecurityUtil() {
    }

    public static String rsaEncrypt(String content, String publicKey) throws Exception {
        PublicKey pubKey = getPublicKeyFromX509("RSA", new ByteArrayInputStream(publicKey.getBytes()));
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(1, pubKey);
        byte[] data = content.getBytes("UTF-8");
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        int i = 0;
        int maxEncryptBlock = 117;
        if (publicKey.length() > 300) {
            maxEncryptBlock = 245;
        }

        while (inputLen - offSet > 0) {
            byte[] cache;
            if (inputLen - offSet > maxEncryptBlock) {
                cache = cipher.doFinal(data, offSet, maxEncryptBlock);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }

            out.write(cache, 0, cache.length);
            ++i;
            offSet = i * maxEncryptBlock;
        }

        byte[] encryptedData = Base64.encodeBase64(out.toByteArray());
        out.close();
        return new String(encryptedData, "UTF-8");
    }

    public static PublicKey getPublicKeyFromX509(String algorithm, InputStream ins) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
        StringWriter writer = new StringWriter();
        StreamUtils.io(new InputStreamReader(ins), writer);
        byte[] encodedKey = writer.toString().getBytes();
        encodedKey = Base64.decodeBase64(encodedKey);
        return keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
    }

    public static PrivateKey getPrivateKeyFromPKCS8(String algorithm, InputStream ins) throws Exception {
        if (ins != null && !StringUtils.isEmpty(algorithm)) {
            KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
            byte[] encodedKey = StreamUtils.readText(ins).getBytes();
            encodedKey = Base64.decodeBase64(encodedKey);
            return keyFactory.generatePrivate(new PKCS8EncodedKeySpec(encodedKey));
        } else {
            return null;
        }
    }

    public static String rsaDecrypt(String content, String privateKey) throws Exception {
        try {
            PrivateKey priKey = getPrivateKeyFromPKCS8("RSA", new ByteArrayInputStream(privateKey.getBytes()));
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(2, priKey);
            byte[] encryptedData = Base64.decodeBase64(content.getBytes("UTF-8"));
            int inputLen = encryptedData.length;
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int offSet = 0;
            int i = 0;
            int maxDecryptBlock = 128;
            if (privateKey.length() > 1000) {
                maxDecryptBlock = 256;
            }

            while (inputLen - offSet > 0) {
                byte[] cache;
                if (inputLen - offSet > maxDecryptBlock) {
                    cache = cipher.doFinal(encryptedData, offSet, maxDecryptBlock);
                } else {
                    cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
                }

                out.write(cache, 0, cache.length);
                ++i;
                offSet = i * maxDecryptBlock;
            }

            byte[] decryptedData = out.toByteArray();
            out.close();
            return new String(decryptedData, "UTF-8");
        } catch (Exception var12) {
            throw new Exception("rsaDecrypt fail:EncodeContent = " + content, var12);
        }
    }

    public static String desEncrypt(String src, String key) throws Exception {
        try {
            SecretKey deskey = new SecretKeySpec(build3DesKey(key), "DESede");
            Cipher c1 = Cipher.getInstance("DESede");
            c1.init(1, deskey);
            byte[] b = c1.doFinal(src.getBytes("UTF-8"));
            return new String(Base64.encodeBase64(b), "UTF-8");
        } catch (Exception var5) {
            throw new Exception("desEncrypt failure:EncodeContent = " + src, var5);
        }
    }

    public static String rsaSign(String content, String privateKey) throws Exception {
        try {
            PrivateKey priKey = getPrivateKeyFromPKCS8("RSA", new ByteArrayInputStream(privateKey.getBytes("UTF-8")));
            Signature signature = Signature.getInstance("SHA1WithRSA");
            signature.initSign(priKey);
            signature.update(content.getBytes("UTF-8"));
            byte[] signed = signature.sign();
            return new String(Base64.encodeBase64(signed));
        } catch (Exception var5) {
            throw new Exception("rsa sign fail:RSAcontent = " + content, var5);
        }
    }

    public static boolean rsaCheckSign(String content, String sign, String publicKey) throws Exception {
        try {
            PublicKey pubKey = getPublicKeyFromX509("RSA", new ByteArrayInputStream(publicKey.getBytes()));
            Signature signature = Signature.getInstance("SHA1WithRSA");
            signature.initVerify(pubKey);
            signature.update(content.getBytes("UTF-8"));
            return signature.verify(Base64.decodeBase64(sign.getBytes()));
        } catch (Exception var5) {
            throw new Exception("rsa checksign fail:RSAcontent=" + content + ",sign=" + sign, var5);
        }
    }

    public static String desDecrypt(String src, String key) throws Exception {
        try {
            SecretKey deskey = new SecretKeySpec(build3DesKey(key), "DESede");
            Cipher c1 = Cipher.getInstance("DESede");
            c1.init(2, deskey);
            byte[] b = c1.doFinal(Base64.decodeBase64(src.getBytes("UTF-8")));
            return new String(b, "UTF-8");
        } catch (Exception var5) {
            throw new Exception("desDecrypt failure:EncodeContent = " + src, var5);
        }
    }

    public static byte[] build3DesKey(String keyStr) throws UnsupportedEncodingException {
        byte[] key = new byte[24];
        byte[] temp = keyStr.getBytes("UTF-8");
        if (key.length > temp.length) {
            System.arraycopy(temp, 0, key, 0, temp.length);
        } else {
            System.arraycopy(temp, 0, key, 0, key.length);
        }

        return key;
    }

    public static String getSortedStr(Object obj) throws Exception {
        Map<String, Object> map = beanToMap(obj);
        return getSortedStr(map);
    }

    public static String getSortedStr(Map<String, Object> map) throws Exception {
        TreeMap<String, Object> treeMap = new TreeMap(map);
        String treeMapStr = JSONObject.toJSONString(treeMap);
        return treeMapStr;
    }

    public static Map<String, Object> beanToMap(Object bean) throws Exception {
        HashMap returnMap = new HashMap();

        try {
            Class<?> type = bean.getClass();
            BeanInfo beanInfo = Introspector.getBeanInfo(type);
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();

            for (int i = 0; i < propertyDescriptors.length; ++i) {
                PropertyDescriptor descriptor = propertyDescriptors[i];
                String propertyName = descriptor.getName();
                // 排除signValue的值
                if (!"class".equals(propertyName) && !"signValue".equals(propertyName)) {
                    Method readMethod = descriptor.getReadMethod();
                    Object result = readMethod.invoke(bean);
                    returnMap.put(propertyName, result);
                }
            }

            return returnMap;
        } catch (Exception var10) {
            throw new Exception("beanToMap失败", var10);
        }
    }

    public static String md5Sign(String content) throws Exception {
        String genSign = "";

        try {
            genSign = DigestUtils.md5Hex(content.getBytes("UTF-8"));
            return genSign;
        } catch (Exception var3) {
            throw new Exception("md5sign fail:EncodeContent = " + content, var3);
        }
    }

    public static boolean md5CheckSign(String content, String sign) throws Exception {
        try {
            String s = md5Sign(content);
            return sign.equals(s);
        } catch (Exception var3) {
            throw new Exception("md5 checksign fail:content=" + content + ",sign=" + sign, var3);
        }
    }

    public static void main(String[] args) throws Exception {
    }
}
