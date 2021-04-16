package com.wanliu.petslink.manage.user.service.Impl;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.google.code.kaptcha.Producer;
import com.wanliu.petslink.common.utils.Base64Util;
import com.wanliu.petslink.common.utils.MD5Util;
import com.wanliu.petslink.manage.test.service.Impl.TestServiceImpl;
import com.wanliu.petslink.manage.user.service.UserService;

/**
 * @author Sunny
 * @create 2021/4/15
 */
@Service
public class UserServiceImpl implements UserService {

    // 日志配置
    private static Logger log = LoggerFactory.getLogger(TestServiceImpl.class);

    // 引入公共资源
    @Autowired
    private Producer producer;
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public Map<String, String> getVaildCode() {
        // 生成验证码
        String text = producer.createText();
        // 存储到redis缓存中
        String vaildCodeKey = "V" + MD5Util.encrypt(UUID.randomUUID().toString());
        // 生成图片源
        BufferedImage image = producer.createImage(text);
        // 创建图片流
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        // 创建返回对象
        Map<String, String> map = new HashMap<>();
        try {
            // 生成对应图片流
            ImageIO.write(image, "jpg", os);
            // 图片base64编码
            String encode = Base64Util.encode(os.toByteArray());
            map.put("img", encode);
            map.put("verifyKey", vaildCodeKey);
            redisTemplate.opsForValue().set(vaildCodeKey, text, 1, TimeUnit.MINUTES);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }
}
