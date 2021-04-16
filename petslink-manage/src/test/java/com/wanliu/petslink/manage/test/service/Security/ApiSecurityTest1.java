package com.wanliu.petslink.manage.test.service.Security;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.alibaba.fastjson.JSONObject;
import com.wanliu.petslink.common.entity.security.RequestBase;
import com.wanliu.petslink.common.entity.security.ResponseBase;
import com.wanliu.petslink.common.utils.security.SecurityHelper;
import com.wanliu.petslink.common.utils.security.SecurityUtil;
import com.wanliu.petslink.common.utils.security.TypeUtil;

/**
 * @author Sunny
 * @create 2021/1/26
 */
@SpringBootTest
@ActiveProfiles("dev")
public class ApiSecurityTest1 {

    // 日志
    private static Logger Log = LoggerFactory.getLogger(ApiSecurityTest1.class);

    // 加签
    private static String signValue(Object content, String type, String rsaPrivateKey) throws Exception {
        String contentString = SecurityUtil.getSortedStr(content);
        String sign;
        if ("a".equals(type)) {
            sign = SecurityUtil.rsaSign(contentString, rsaPrivateKey);
        } else {
            sign = SecurityUtil.md5Sign(contentString);
        }

        return sign;
    }

    // 加密
    private static String encrypt(Object content, String publicKey, String type) throws Exception {
        String contentString = TypeUtil.obj2Str(content);
        String result = "";
        if ("a".equals(type)) {
            result = SecurityUtil.rsaEncrypt(contentString, publicKey);
        } else if ("b".equals(type)) {
            result = SecurityUtil.desEncrypt(contentString, publicKey);
        }
        return result;
    }

    // 解密
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

    @Test
    public void test() throws Exception {

        // 公钥
        String publicKey = "d41d8cd98f00b204e9800998ecf8427e";
        // 加密方式 a:rsa加密签名 b:des加密
        String securityType = "b";

        // 模拟请求或返回参数
        String jsonParams = "{\n" + "    \"HEAD\":{\n"
                + "        \"TransRefGUID\":\"6653386caf0a460dbb4ff47710ae3f3a\",\n"
                + "        \"TransType\":\"YUG001\",\n" + "        \"TransExeDate\":\"2021-01-29\",\n"
                + "        \"TransExeTime\":\"11:45:50\"\n" + "    },\n" + "    \"BODY\":{\n"
                + "        \"GrpContNo\":\"GP4421000063\",\n" + "        \"EdorType\":\"NI\",\n"
                + "        \"EdorAppDate\":\"2021-01-29\",\n" + "        \"InsuredList\":{\n"
                + "            \"Insured\":{\n" + "                \"Name\":\"Sherrly\",\n"
                + "                \"Sex\":\"1\",\n" + "                \"Birthday\":\"1990-03-01\",\n"
                + "                \"IDType\":\"1\",\n" + "                \"IDNo\":\"H94324222\",\n"
                + "                \"JobCode\":\"1050101\",\n" + "                \"JobLeve\":\"1\",\n"
                + "                \"JoinCompanyDate\":\"\",\n" + "                \"Mobile\":\"13555445222\",\n"
                + "                \"InsuredPeoples\":\"1\",\n" + "                \"WorkNo\":\"\",\n"
                + "                \"Email\":\"\",\n" + "                \"MedicareStatus\":\"1\",\n"
                + "                \"ContPlanCode\":\"3\",\n" + "                \"GrpPayMode\":\"1\",\n"
                + "                \"GrpBankCode\":\"\",\n" + "                \"GrpBankAccName\":\"\",\n"
                + "                \"GrpBankAccNo\":\"\",\n" + "                \"SelfPayMoney\":\"0.0\",\n"
                + "                \"GrpPayMoney\":\"20.0\",\n" + "                \"ContCValiDate\":\"2021-01-29\",\n"
                + "                \"GrpCompanFree\":\"\",\n" + "                \"AccountCode\":\"\",\n"
                + "                \"Salary\":\"\",\n" + "                \"ExecuteCom\":\"\",\n"
                + "                \"CertifyCode\":\"\",\n" + "                \"StartCode\":\"\",\n"
                + "                \"MainInsuredName\":\"\",\n" + "                \"MainInsuredNo\":\"\",\n"
                + "                \"MainRelation\":\"\",\n" + "                \"SubsidiaryInsuredFlag\":\"1\",\n"
                + "                \"EmployeeName\":\"\",\n" + "                \"EmployeeIdType\":\"\",\n"
                + "                \"EmpolyeeIdNo\":\"\",\n" + "                \"EmployeeRelation\":\"\",\n"
                + "                \"EmployeeNo\":\"\",\n" + "                \"HealthFlag\":\"\",\n"
                + "                \"RelationToAppnt\":\"06\",\n" + "                \"IdExpDate\":\"\",\n"
                + "                \"Nationality\":\"AUT\",\n" + "                \"BnfList\":\"\",\n"
                + "                \"RiskList\":\"\"\n" + "            }\n" + "        },\n"
                + "        \"ESViewList\":{\n" + "            \"ESView\":{\n" + "                \"SubType\":\"\",\n"
                + "                \"PageNum\":\"\",\n" + "                \"PageList\":{\n"
                + "                    \"Page\":{\n" + "                        \"ImageUrl\":\"\",\n"
                + "                        \"ImageName\":\"\",\n" + "                        \"PageCode\":\"\"\n"
                + "                    }\n" + "                }\n" + "            }\n" + "        }\n" + "    }\n"
                + "}";
        Object jsonParamsObject = JSONObject.parse(jsonParams);

        /**
         * 处理请求
         */
        // 请求报文格式
        RequestBase requestBase = new RequestBase();
        requestBase.setServiceName("edorAddInsured");
        requestBase.setAppKey("27199455-2192-4d14-bbc2-433adfedf139");
        requestBase.setVersion("1.0");
        requestBase.setTimestamp(String.valueOf(System.currentTimeMillis()));
        requestBase.setBizContent(jsonParamsObject);
        SecurityHelper.encryptAndSign(requestBase, publicKey, securityType, "");
        Log.info("加密，加签后的数据：{}", JSONObject.toJSONString(requestBase));

        // 处理请求报文
        // 1.对比签文
        String signValue = signValue(requestBase, securityType, "");
        Log.info("signValue：{}", signValue);
        // 2.解密
        String decrypt = decrypt(requestBase.getBizContent(), publicKey, securityType, "");
        Log.info("解密后的请求报文：{}", decrypt);

        /**
         * 处理返回
         */
        // 返回报文格式
        ResponseBase responseBase = new ResponseBase();
        responseBase.setCode("200");
        responseBase.setMessage("成功");
        responseBase.setTimestamp(String.valueOf(System.currentTimeMillis()));
        // 对返回数据加密
        String encrypt = encrypt(jsonParamsObject, publicKey, securityType);
        responseBase.setBizContent(encrypt);
        // 对返回数据加签
        String signValue1 = signValue(responseBase, securityType, "");
        responseBase.setSignValue(signValue1);

        // 处理返回报文
        Log.info("返回数据：{}", JSONObject.toJSONString(responseBase));
        String result = SecurityHelper.checkSignAndDecrypt(responseBase, publicKey, securityType, "");
        Log.info("解密返回数据：{}", JSONObject.toJSONString(result));

    }

}
