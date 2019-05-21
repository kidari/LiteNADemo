package com.huawei.service.subscribtionManagement;

import java.util.HashMap;
import java.util.Map;

import com.huawei.utils.Constant;
import com.huawei.utils.HttpsUtil;
import com.huawei.utils.JsonUtil;
import com.huawei.utils.StreamClosedHttpResponse;

/**
 * Delete Specify Subscription :
 * This interface is used to delete a subscription by subscription ID.
 */
public class DeleteSpecifySubscription {

    public static void main(String args[]) throws Exception {

        // Two-Way Authentication
        HttpsUtil httpsUtil = new HttpsUtil();
        httpsUtil.initSSLConfigForTwoWay();

        // Authentication，get token
        String accessToken = login(httpsUtil);

        //please replace the subscriptionId, when you use the demo.
        String subscriptionId = "5bcaddda-75bf-4623-8c8d-26175c41fcca";
        
        //Please make sure that the following parameter values have been modified in the Constant file.
        String appId = Constant.APPID;
        String urlDeleteSpecifySubscription = Constant.DELETE_SPECIFY_SUBSCRIPTION + "/" + subscriptionId;

        Map<String, String> header = new HashMap<>();
        header.put(Constant.HEADER_APP_KEY, appId);
        header.put(Constant.HEADER_APP_AUTH, "Bearer" + " " + accessToken);

        StreamClosedHttpResponse responseDeleteSpecifySubscription = httpsUtil.doDeleteWithParasGetStatusLine(urlDeleteSpecifySubscription, null, header);

        System.out.println("DeleteSpecifySubscription, response content:");
        System.out.println(responseDeleteSpecifySubscription.getStatusLine());
        System.out.println(responseDeleteSpecifySubscription.getContent());
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
