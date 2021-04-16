package com.wanliu.petslink.common.utils.security;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpUtil {

    private static final Logger log = LoggerFactory.getLogger(HttpUtil.class);

    /**
     * 向指定URL发送GET方法的请求
     * 
     * @param urlNameString
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGet(String urlNameString) {
        String result = "";
        BufferedReader in = null;
        try {
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("contentType", "utf-8");
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                log.info(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            log.info("发送GET请求出现异常！" + e);
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                log.info("", e2);
            }
        }
        return result;
    }

    /**
     * 向指定 URL 发送POST方法的请求
     * 
     * @param url
     *            发送请求的 URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("contentType", "utf-8");
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            log.info("发送 POST 请求出现异常！" + e);
        }
        // 使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                log.info("", ex);
            }
        }
        return result;
    }

    public static String postHttpRequestJson(String url, String requestJson) {
        log.info("请求地址:" + url);
        // 接收结果
        String tResult = "";
        try {
            // 创建HttpClient对象
            HttpClient client = new HttpClient();
            // 加载地址以POST方式提交
            PostMethod method = new PostMethod(url);
            // 设置编码
            method.setRequestHeader("Accept", "application/json");
            method.setRequestHeader("Content-Type", "application/json");
            method.setRequestHeader("Encoding", "UTF-8");
            method.setRequestEntity(new StringRequestEntity(requestJson, "application/json", "UTF-8"));
            // 执行
            int result = client.executeMethod(method);
            if (result != HttpStatus.SC_OK) {
                log.info("请求失败！" + result);
            } else {
                log.info("请求成功！" + result);
                tResult = method.getResponseBodyAsString();
            }
            log.info("返回报文:" + tResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tResult;
    }

    public static String postHttpRequestJson(String url, String requestJson, String app_id, String app_secret) {
        // 接收结果
        String tResult = "";
        try {
            // 创建HttpClient对象
            HttpClient client = new HttpClient();
            // 加载地址以POST方式提交
            PostMethod method = new PostMethod(url);
            // 设置编码
            method.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
            method.setRequestHeader("app_id", app_id);
            method.setRequestHeader("app_secret", app_secret);
            method.setRequestEntity(new StringRequestEntity(requestJson, "application/json", "UTF-8"));
            // 执行
            int result = client.executeMethod(method);
            if (result != HttpStatus.SC_OK) {
                log.info("请求失败！" + result);
            } else {
                log.info("请求成功！" + result);
                tResult = method.getResponseBodyAsString();
            }
            log.info("返回报文:" + tResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tResult;
    }

    // HTTP post请求 xml入参的形式
    public static String postHttpRequestXMl(String url, String xmlString) {
        // 接收结果
        String tResult = "";
        try {
            // 创建HttpClient对象
            HttpClient client = new HttpClient();
            // 加载地址以POST方式提交
            PostMethod methodPost = new PostMethod(url);
            // 设置请求头部类型
            methodPost.setRequestHeader("Content-Type", "text/xml");
            methodPost.setRequestHeader("charset", "utf-8");
            methodPost.setRequestEntity(new StringRequestEntity(xmlString, "text/xml", "utf-8"));
            // 执行
            int result = client.executeMethod(methodPost);
            if (result != HttpStatus.SC_OK) {
                log.info("请求失败！" + result);
            } else {
                log.info("请求成功！" + result);
                tResult = methodPost.getResponseBodyAsString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tResult;
    }

    public static String post(String apiUrl, String params) throws ClientProtocolException, IOException {
        HttpPost httpPost = new HttpPost(apiUrl);
        CloseableHttpClient client = HttpClients.createDefault();
        String respContent = null;
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.addTextBody("charset", "UTF-8");
        builder.addTextBody("result", params);
        HttpEntity multipart = builder.build();
        HttpResponse resp = null;
        try {
            httpPost.setEntity(multipart);
            resp = client.execute(httpPost);
            HttpEntity he = resp.getEntity();
            // 注意，返回的结果的状态码是302，非200
            if (resp.getStatusLine().getStatusCode() == 302) {
                respContent = EntityUtils.toString(he, "UTF-8");
            }
            respContent = EntityUtils.toString(he, "UTF-8");
            log.info("请求成功！" + respContent);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return respContent;
    }

    // 获取当前服务器ip
    public static String getIp() {
        try {
            InetAddress ia = InetAddress.getLocalHost();
            String localip = ia.getHostAddress();
            return localip;
        } catch (Exception e) {
            e.printStackTrace();
            return "500";
        }
    }

    public static void main(String[] args) throws ParseException, ClientProtocolException, IOException {
        System.out.println(sendGet(
                "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wxca6dde3b3a3b89cc&secret=APPSECRET"));

        // Map<String, Object> param = new HashMap<>();
        // param.put("policyNo", "P4420164200041");
        // param.put("policyType", "E");
        // Map<String, Object> params = new HashMap<>();
        // params.put("result", param);
        // post("http://print.e-hqins.com/DZDYWS/hengqin/dataSearch.action",
        // JSONObject.toJSONString(param));
        // String
        // string="{\"tranData\":{\"body\":{\"contInfo\":{\"agentCode\":\"1440000725\",\"agentImpartList\":[],\"agentManageCom\":\"8644989801\",\"agentType\":\"245\",\"appntInfo\":{\"address\":\"北京市市辖区东城区东四\",\"appntImpartList\":[],\"birthday\":\"2000-01-01\",\"company\":\"三峡财务有限责任公司\",\"email\":\"\",\"idExpDate\":\"2023-06-28\",\"idNo\":\"110101200001012024\",\"idType\":\"0\",\"jobCode\":\"1050104\",\"mobile\":\"15001110001\",\"name\":\"文艺\",\"nationality\":\"CHN\",\"sex\":\"1\",\"taxpayerType\":\"01\",\"yearSalary\":\"\",\"zipCode\":\"123222\"},\"contTypeFlag\":\"2\",\"cvalidate\":\"\",\"esViewList\":[],\"grpContNo\":\"GP4420930259\",\"grpPayMode\":\"\",\"grpPrtNo\":\"A51104100000915\",\"insuredInfoList\":[{\"accBankCode\":\"\",\"accBankProvince\":\"\",\"accName\":\"\",\"accNo\":\"\",\"address\":\"北京市市辖区东城区健健康康快快乐乐健健康康\",\"birthday\":\"2002-06-19\",\"bnfList\":[],\"contNo\":\"994420000001362\",\"contPlanCode\":\"\",\"email\":\"\",\"idNo\":\"Gh16673737\",\"idType\":\"1\",\"insuredImpartList\":[{\"impartCode\":\"11101\",\"impartContent\":\"下述哪种描述最符合您的吸烟情况？①平均每日吸烟2包以上;
        // ②平均每日吸烟1包-2包; ③平均每日吸烟1包以内; ④从不吸烟
        // 您是否已戒烟，若“是”，请详述戒烟时间及原因__________________________________________。\",\"impartReply\":\"1/否/、无/\",\"impartVer\":\"A001\",\"introDuctions\":\"\"},{\"impartCode\":\"11102\",\"impartContent\":\"下述哪种描述最符合您的饮酒情况？。①平均每日饮酒白酒1斤以上，或啤酒8瓶，或红酒2瓶以上；②平均每日饮酒白酒半斤以内，或啤酒4瓶，或红酒1瓶以内；
        // ③饮酒量在第1项和第2项间；④偶尔或社交性饮酒；⑤从不饮酒
        // 您是否已戒酒，若“是”，请详述戒酒时间及原因___________________________
        // 。\",\"impartReply\":\"1/否/、无/\",\"impartVer\":\"A001\",\"introDuctions\":\"\"},{\"impartCode\":\"11103-A\",\"impartContent\":\"是否目前或过去曾进行过以下检查或治疗？A．
        // 一年内曾去医院进行过门诊的检查、服药、手术或其他治疗。\",\"impartReply\":\"否/\",\"impartVer\":\"A001\",\"introDuctions\":\"\"},{\"impartCode\":\"11103-B\",\"impartContent\":\"是否目前或过去曾进行过以下检查或治疗？B．
        // 二年内曾有医学检查（包括健康体检）结果异常。\",\"impartReply\":\"否/\",\"impartVer\":\"A001\",\"introDuctions\":\"\"},{\"impartCode\":\"11103-C\",\"impartContent\":\"是否目前或过去曾进行过以下检查或治疗？C．
        // 五年内曾住院检查或治疗（包括入住疗养院、康复医院等医疗机构）。\",\"impartReply\":\"否/\",\"impartVer\":\"A001\",\"introDuctions\":\"\"},{\"impartCode\":\"11104\",\"impartContent\":\"您是否目前或过去一年内曾有过下列症状？反复头痛或眩晕、晕厥、咯血、胸痛、呼吸困难、呕血、黄疸、便血、听力下降、耳鸣、复视、视力明显下降、原因不明皮肤病和粘膜及齿龈出血、原因不明的发热、体重下降超过5公斤、原因不明的肌肉萎缩、原因不明的包块或肿物、身体的其他感觉异常或活动障碍。\",\"impartReply\":\"否/\",\"impartVer\":\"A001\",\"introDuctions\":\"\"},{\"impartCode\":\"11105-A\",\"impartContent\":\"是否目前患有或过去曾经患过下列疾病或手术史？A．
        // 脑、神经系统及精神方面疾病，例如：癫痫、脑中风、脑炎、脑膜炎、脑血管瘤、运动神经元病、阿尔茨海默氏症、帕金森氏综合症、脊髓疾病、重症肌无力、多发性硬化、抑郁症、精神病、脑部手术史。\",\"impartReply\":\"否/\",\"impartVer\":\"A001\",\"introDuctions\":\"\"},{\"impartCode\":\"11105-B\",\"impartContent\":\"是否目前患有或过去曾经患过下列疾病或手术史？B．
        // 心血管系统疾病，例如：高血压、冠心病、心绞痛、心律失常、心肌梗塞、先天性心脏病、风湿性心脏病、心肌病、室壁瘤、动脉瘤、心脏瓣膜病、主动脉疾病、下肢静脉曲张。\",\"impartReply\":\"否/\",\"impartVer\":\"A001\",\"introDuctions\":\"\"},{\"impartCode\":\"11105-C\",\"impartContent\":\"是否目前患有或过去曾经患过下列疾病或手术史？C．
        // 呼吸系统疾病，例如：慢性支气管炎、肺气肿、肺心病、哮喘、肺结核、肺栓塞、支气管扩张、尘肺、间质性肺病、肺纤维化。\",\"impartReply\":\"否/\",\"impartVer\":\"A001\",\"introDuctions\":\"\"},{\"impartCode\":\"11105-D\",\"impartContent\":\"是否目前患有或过去曾经患过下列疾病或手术史？D．
        // 消化系统疾病，例如：胃和/或十二指肠溃疡、胰腺炎、肝炎（请注明类型）、乙肝或丙肝病毒携带、多囊肝、肝内胆管炎、肝硬化、胆结石、慢性或溃疡性结肠炎、克隆病、腹部手术史。\",\"impartReply\":\"否/\",\"impartVer\":\"A001\",\"introDuctions\":\"\"},{\"impartCode\":\"11105-E\",\"impartContent\":\"是否目前患有或过去曾经患过下列疾病或手术史？E．
        // 泌尿系统疾病，
        // 例如：血尿、蛋白尿、尿路畸形、肾炎、肾病、肾脏功能不全、尿毒症、肾移植、肾积水、肾囊肿、泌尿系统结石、泌尿系统手术史。\",\"impartReply\":\"否/\",\"impartVer\":\"A001\",\"introDuctions\":\"\"},{\"impartCode\":\"11105-F\",\"impartContent\":\"是否目前患有或过去曾经患过下列疾病或手术史？F．
        // 骨骼、肌肉、结缔组织的疾病，例如：类风湿性关节炎、强直性脊柱炎、椎管狭窄、脊柱裂、股骨头坏死、骨性关节炎、骨髓炎、皮肌炎、肌营养不良症、干燥综合症、系统性红斑狼疮。\",\"impartReply\":\"否/\",\"impartVer\":\"A001\",\"introDuctions\":\"\"},{\"impartCode\":\"11105-G\",\"impartContent\":\"是否目前患有或过去曾经患过下列疾病或手术史？G．
        // 内分泌、血液系统、免疫系统疾病，例如：糖尿病、痛风、甲状腺结节、甲状腺或甲状旁腺疾病、白血病、血友病、再生障碍性贫血、地中海贫血、艾滋病或艾滋病病毒（HIV）携带。\",\"impartReply\":\"否/\",\"impartVer\":\"A001\",\"introDuctions\":\"\"},{\"impartCode\":\"11105-H\",\"impartContent\":\"是否目前患有或过去曾经患过下列疾病或手术史？H．
        // 五官科疾病，例如：视网膜出血或剥离、青光眼、白内障、高度近视（800度以上）、美尼尔病、五官手术史\",\"impartReply\":\"否/\",\"impartVer\":\"A001\",\"introDuctions\":\"\"},{\"impartCode\":\"11105-I\",\"impartContent\":\"是否目前患有或过去曾经患过下列疾病或手术史？I．
        // 以上未提及的肿瘤，包括：肉瘤、癌、良性肿瘤、息肉、囊肿。\",\"impartReply\":\"否/\",\"impartVer\":\"A001\",\"introDuctions\":\"\"},{\"impartCode\":\"11106\",\"impartContent\":\"您是否曾有滥用药物或服用毒品？若“是”，请在说明栏告知\",\"impartReply\":\"否/\",\"impartVer\":\"A001\",\"introDuctions\":\"\"},{\"impartCode\":\"11107\",\"impartContent\":\"您是否有智能障碍？是否有失明、聋哑及言语、咀嚼或身体其他部位缺损、残疾或功能障碍？若“是”，请在说明栏说明智能障碍等级、残疾部位（哪侧）、原因、有无功能障碍、是否使用辅助器械。\",\"impartReply\":\"否/\",\"impartVer\":\"A001\",\"introDuctions\":\"\"},{\"impartCode\":\"11108\",\"impartContent\":\"您的父母、子女、兄弟姐妹是否有患癌症、心脑血管病症、白血病、血友病、糖尿病、多囊肝、多囊肾、肠息肉或其他遗传性疾病？（若是，请在下表告知）\",\"impartReply\":\"否/\",\"impartVer\":\"A001\",\"introDuctions\":\"\"},{\"impartCode\":\"11109-A\",\"impartContent\":\"女性告知事项（适用于≥18周岁）：A．
        // 您是否目前怀孕？若“是”,怀孕______周。\",\"impartReply\":\"否/无/\",\"impartVer\":\"A001\",\"introDuctions\":\"\"},{\"impartCode\":\"11109-B\",\"impartContent\":\"女性告知事项（适用于≥18周岁）：B．
        // 您怀孕及生产期间是否有合并症？例如：蛋白尿、高血压、糖尿病、宫外孕等。\",\"impartReply\":\"否/\",\"impartVer\":\"A001\",\"introDuctions\":\"\"},{\"impartCode\":\"11109-C\",\"impartContent\":\"女性告知事项（适用于≥18周岁）：C．
        // 您是否有阴道异常流血、畸胎瘤、葡萄胎、盆腔炎或其他任何乳房、子宫、卵巢的疾病？\",\"impartReply\":\"否/\",\"impartVer\":\"A001\",\"introDuctions\":\"\"},{\"impartCode\":\"11110-A\",\"impartContent\":\"少儿告知事项（适用于≤2周岁被保险人）：A．
        // 出生时体重______公斤。\",\"impartReply\":\"无/\",\"impartVer\":\"A001\",\"introDuctions\":\"\"},{\"impartCode\":\"11110-B\",\"impartContent\":\"少儿告知事项（适用于≤2周岁被保险人）：B．
        // 是否为双胞胎或多胞胎？是否为早产、难产？出生时是否曾有产伤、窒息等异常情况？\",\"impartReply\":\"否/否/否/\",\"impartVer\":\"A001\",\"introDuctions\":\"\"},{\"impartCode\":\"11110-C\",\"impartContent\":\"少儿告知事项（适用于≤2周岁被保险人）：C．
        // 是否有畸形、发育迟缓、惊厥、抽搐、脑瘫、先天性或遗传性疾病？\",\"impartReply\":\"否/\",\"impartVer\":\"A001\",\"introDuctions\":\"\"},{\"impartCode\":\"11201-A\",\"impartContent\":\"A.您是否有机动车驾驶执照？若“有”，请告知驾照的类型__________
        // 。\",\"impartReply\":\"否/无/\",\"impartVer\":\"A002\",\"introDuctions\":\"\"},{\"impartCode\":\"11201-B\",\"impartContent\":\"B.您是否曾违章驾车并发生交通事故？若“是”，请详述次数、时间、违章类型
        // ____________________。\",\"impartReply\":\"否/无/\",\"impartVer\":\"A002\",\"introDuctions\":\"\"},{\"impartCode\":\"11202\",\"impartContent\":\"您是否有参加赛车、赛马、搏击类运动、攀岩、潜水、滑雪、蹦极、飞行、探险、特技活动及其它高风险活动的爱好？若“是”，请详述参加的项目以及每年大约参加的次数_____________________________________。\",\"impartReply\":\"否/无/\",\"impartVer\":\"A002\",\"introDuctions\":\"\"},{\"impartCode\":\"11203-A\",\"impartContent\":\"A.在过去的两年中，您是否在本地以外的国家或地区（包括外地或境外）连续居住超过三个月？若“是”，居住的国家或地区：___________，居住时间___________个月。\",\"impartReply\":\"否/无/无/\",\"impartVer\":\"A002\",\"introDuctions\":\"\"},{\"impartCode\":\"11203-B\",\"impartContent\":\"B.未来一年，您是否计划出国？若“是”，请说明计划前往的国家或地区___________，目的___________，居住时间
        // ___________个月。\",\"impartReply\":\"否/无/无/无/\",\"impartVer\":\"A002\",\"introDuctions\":\"\"},{\"impartCode\":\"11204\",\"impartContent\":\"您目前是否已有或正在申请除本公司以外的人身保险？
        // 若“是”请详述（如内容较多请填写于备注栏）\",\"impartReply\":\"否/\",\"impartVer\":\"A002\",\"introDuctions\":\"\"},{\"impartCode\":\"11205\",\"impartContent\":\"您是否投保其他保险公司的下列产品时，被非标准体承保或申请过理赔？
        // 产品类型：①人寿保险 ②重大疾病保险 ③住院医疗保险 ④意外保险
        // \",\"impartReply\":\"否/无/无/\",\"impartVer\":\"A002\",\"introDuctions\":\"\"}],\"mainRelation\":\"02\",\"mobile\":\"15701269903\",\"name\":\"哦哦哦\",\"nationality\":\"AFG\",\"newPayMode\":\"\",\"occupationCode\":\"2022111\",\"occupationType\":\"1\",\"riskList\":[{\"amnt\":770000,\"dutyList\":[{\"amnt\":770000,\"calRule\":\"\",\"dutyCode\":\"ID6380\",\"getLimit\":0,\"getRate\":1,\"prem\":5944.4}],\"insuYear\":\"85\",\"insuYearFlag\":\"A\",\"mainRiskCode\":\"16380\",\"mult\":\"1\",\"pay\":{\"personPayMode\":\"4\",\"personPayMoney\":5944.4},\"payEndYear\":\"20\",\"payEndYearFlag\":\"Y\",\"payIntv\":\"12\",\"payYears\":\"20\",\"prem\":5944.4,\"riskCode\":\"16380\",\"years\":\"85\"}],\"sex\":\"1\",\"socialInsuFlag\":\"0\",\"stature\":\"167\",\"weight\":\"77\",\"zipCode\":\"061300\"}],\"manageCom\":\"8644989801\",\"polApplyDate\":\"2020-06-19\",\"prtNo\":\"994420000001362\",\"saleChnl\":\"01\",\"sellType\":\"01\"}},\"head\":{\"funcFlag\":\"YF0003\",\"source\":\"D0F28136208FBA26707C936071057EA0\",\"subSource\":\"01\",\"tranDate\":\"2020-06-19\",\"tranTime\":\"18:12:30\",\"transRefGUID\":\"29a823316acc4f958f7f8aaa4c3fe486\"}}}";
        // postHttpRequestJson("http://10.9.10.216:8100/gateway/group-insure-service/singleInsureApply/preUnderWriting",
        // string);
        // OcrRepuest ocrRepuest = new OcrRepuest();
        // ocrRepuest.setTransCode("IS_OCR_001");
        // ocrRepuest.setTransTime("2020-04-17 15:01:10");
        // ocrRepuest.setChannelSource("7");
        // ocrRepuest.setActionType("API");
        // ocrRepuest.setAction("IDCard");
        // ocrRepuest.setCardSide("FRONT");
        // ocrRepuest.setImageBase64(Base64AndMD5Util.ImageToBase64("C:\\Users\\Administrator\\Desktop\\idCard\\id_card2.png"));
        // String jsonString = JSONObject.toJSONString(ocrRepuest);
        // log.info(jsonString);
        //
        // String sendPost =
        // postHttpRequestJson("https://ishare-gw-uat.e-hqins.com/intelligence/ocr/all",
        // jsonString, "yf-ocr", "1FD3bAadF1933B98");
        // JSONObject jsStr = JSONObject.parseObject(sendPost);
        // OcrResponse ocrResponse = JSONObject.toJavaObject(jsStr, OcrResponse.class);
        // System.out.println(ocrResponse.getCode());

        // PayCenterGetPayUrl payCenterGetPayUrl = new PayCenterGetPayUrl();
        // payCenterGetPayUrl.setTransSource("YF");
        // payCenterGetPayUrl.setTransCode("ISHARE-PAY-API-GETPAYURL");
        // payCenterGetPayUrl.setTransNo("000000000006");
        // payCenterGetPayUrl.setTransTime("2020-04-17 15:01:10");
        // payCenterGetPayUrl.setPayKind("H5");
        // payCenterGetPayUrl.setChannelCode("11");
        // payCenterGetPayUrl.setBusinessNo("54754754754757777");
        // payCenterGetPayUrl.setAmount(1000.00);
        // payCenterGetPayUrl.setPageBackUrl("https://www.baidu.com");
        // payCenterGetPayUrl.setDataBackUrl("http://10.9.10.164:8081/hqeflex/services/payStatusNotice");
        // payCenterGetPayUrl.setCustomName("韩乐");
        // payCenterGetPayUrl.setIdCardType("0");
        // payCenterGetPayUrl.setIdCardNo("130925199210185119");
        // payCenterGetPayUrl.setMobile("15701269903");
        // payCenterGetPayUrl.setCustomId("130925199210185119");
        // payCenterGetPayUrl.setBusinessDesc("横琴福裕团体重大疾病保险");
        // payCenterGetPayUrl.setOnlyPkFlag(false);
        // payCenterGetPayUrl.setPkChooseFlag(true);
        // String jsonString = JSONObject.toJSONString(payCenterGetPayUrl);
        // log.info(jsonString);
        //
        // String sendPost =
        // postHttpRequestJson("http://172.16.15.103:9527/service/api-getPayUrl",
        // jsonString, "yf-pay", "ibsnz5rytovfv18ohSb2");
        // JSONObject jsStr = JSONObject.parseObject(sendPost);
        // PayCenterGetPayUrlResponse payCenterGetPayUrlResponse =
        // JSONObject.toJavaObject(jsStr, PayCenterGetPayUrlResponse.class);
        // System.out.println(payCenterGetPayUrlResponse.getData().getPayUrl());

        // PayCenterPayStatusQuery payCenterPayStatusQuery = new
        // PayCenterPayStatusQuery();
        // payCenterPayStatusQuery.setTransSource("YF");//交易系统
        // payCenterPayStatusQuery.setTransCode("PAY_CENTER_PAY_STATUS_QUERY");//交易编码
        // payCenterPayStatusQuery.setTransTime("2020-04-17 15:01:10");//交易时间
        // payCenterPayStatusQuery.setTransNo("3463464364364666");//交易流水号
        // payCenterPayStatusQuery.setChannelCode("11");//渠道
        // payCenterPayStatusQuery.setBusinessNo("3463464364364666");//业务单号
        // payCenterPayStatusQuery.setPayTransactionNo("");//支付交易号
        // String result =
        // postHttpRequestJson("https://ishare-gw-uat.e-hqins.com/pay/api/queryPayStatus",
        // JSONObject.toJSONString(payCenterPayStatusQuery), "yf-pay",
        // "ibsnz5rytovfv18ohSb2");
        // log.info(result);
    }
}
