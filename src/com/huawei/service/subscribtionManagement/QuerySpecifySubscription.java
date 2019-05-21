package com.huawei.service.subscribtionManagement;

import java.util.HashMap;
import java.util.Map;

import com.huawei.utils.Constant;
import com.huawei.utils.HttpsUtil;
import com.huawei.utils.JsonUtil;
import com.huawei.utils.StreamClosedHttpResponse;

/**
 * Query Specify Subscription :
 * This interface is used to query a subscription information by subscription ID.
 */
public class QuerySpecifySubscription {

    public static void main(String args[]) throws Exception {

        // Two-Way Authentication
        HttpsUtil httpsUtil = new HttpsUtil();
        httpsUtil.initSSLConfigForTwoWay();

        // Authentication，get token
        String accessToken = login(httpsUtil);

        //please replace the subscriptionId, when you use the demo.
        String subscriptionId = "df3b62f7-aeb3-4c7b-a448-2fd3c9673f6e";
        
        //Please make sure that the following parameter values have been modified in the Constant file.
        String appId = Constant.APPID;
        String urlQuerySpecifySubscription = Constant.QUERY_SPECIFY_SUBSCRIPTION + "/" + subscriptionId;

        Map<String, String> header = new HashMap<>();
        header.put(Constant.HEADER_APP_KEY, appId);
        header.put(Constant.HEADER_APP_AUTH, "Bearer" + " " + accessToken);

        StreamClosedHttpResponse responseQuerySpecifySubscription = httpsUtil.doGetWithParasGetStatusLine(urlQuerySpecifySubscription, null, header);

        System.out.println("QuerySpecifySubscription, response content:");
        System.out.println(responseQuerySpecifySubscription.getStatusLine());
        System.out.println(responseQuerySpecifySubscription.getContent());
        System.out.println();
        
    }


    /**
     * Authentication，get token
     */
    @SuppressWarnings("unchecked")
    public static String login(HttpsUtil httpsUtil) throws Exception {

        String appId = Constant.APPID;
        String secret = Constant.SECRET;
        String urlLogin = Constant.APP_AUTH;

        Map<String, String> paramLogin = new HashMap<>();
        paramLogin.put("appId", appId);
        paramLogin.put("secret", secret);

        StreamClosedHttpResponse responseLogin = httpsUtil.doPostFormUrlEncodedGetStatusLine(urlLogin, paramLogin);

        System.out.println("app auth success,return accessToken:");
        System.out.println(responseLogin.getStatusLine());
        System.out.println(responseLogin.getContent());
        System.out.println();

        Map<String, String> data = new HashMap<>();
        data = JsonUtil.jsonString2SimpleObj(responseLogin.getContent(), data.getClass());
        return data.get("accessToken");
    }

}
