package com.wanliu.petslink.common.utils;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

/**
 * @author Sunny
 * @create 2021/4/16 shiro框架的写法
 */
public class MD5Util {

    // salt代表盐，需要加进一起加密的数据
    private static final String SALT = "petslink_0708";

    // algorith_Name代表进行加密的算法名称
    private static final String ALGORITH_NAME = "md5";

    // hashIterations代表hash迭代次数
    private static final int HASH_ITERATIONS = 2;

    // 加密
    public static String encrypt(String pwd) {
        String newPassword = new SimpleHash(ALGORITH_NAME, pwd, ByteSource.Util.bytes(SALT), HASH_ITERATIONS).toHex();
        return newPassword;
    }

    // 一般来说盐就是用户的姓名
    public static String encrypt(String username, String pwd) {
        String newPassword = new SimpleHash(ALGORITH_NAME, pwd, ByteSource.Util.bytes(username), HASH_ITERATIONS)
                .toHex();
        return newPassword;
    }
}
