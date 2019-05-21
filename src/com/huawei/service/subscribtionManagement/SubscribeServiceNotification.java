package com.huawei.service.subscribtionManagement;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;

import com.huawei.utils.Constant;
import com.huawei.utils.HttpsUtil;
import com.huawei.utils.JsonUtil;
import com.huawei.utils.StreamClosedHttpResponse;

/**
 * SubscribeServiceNotification :
 * This interface is used to subscribe service data of IoT platform.
 */
public class SubscribeServiceNotification {

    public static void main(String args[]) throws Exception {

        // Two-Way Authentication
        HttpsUtil httpsUtil = new HttpsUtil();
        httpsUtil.initSSLConfigForTwoWay();

        // Authentication，get token
        String accessToken = login(httpsUtil);

        //Please make sure that the following parameter values have been modified in the Constant file.
        String appId = Constant.APPID;
        String urlSubscribeServiceNotification = Constant.SUBSCRIBE_SERVICE_NOTIFYCATION;

        /*
         * please replace the notifyType, when you use the demo.
         * service Notify Type:
         * deviceAdded|bindDevice|deviceInfoChanged|deviceDataChanged|deviceDatasChanged|
         * deviceDeleted|messageConfirm|commandRsp|deviceEvent|serviceInfoChanged|
         * ruleEvent|deviceModelAdded|deviceModelDeleted|
         * deviceDesiredPropertiesModifyStatusChanged
         */
        String notifyType = "deviceAdded";
        
        //Please make sure that the value of callbackurl have been modified in the Constant file.
        //And choose the callbackurl according to the notifyType.
        String callbackurl = Constant.DEVICE_ADDED_CALLBACK_URL;
        
        Map<String, Object> paramSubscribe = new HashMap<>();
        paramSubscribe.put("notifyType", notifyType);
        paramSubscribe.put("callbackUrl", callbackurl);
        paramSubscribe.put("appId", appId);

        String jsonRequest = JsonUtil.jsonObj2Sting(paramSubscribe);

        Map<String, String> header = new HashMap<>();
        header.put(Constant.HEADER_APP_KEY, appId);
        header.put(Constant.HEADER_APP_AUTH, "Bearer" + " " + accessToken);

        HttpResponse httpResponse = httpsUtil.doPostJson(urlSubscribeServiceNotification, header, jsonRequest);

        String bodySubscribe = httpsUtil.getHttpResponseBody(httpResponse);

        System.out.println("SubscribeServiceNotification: " + notifyType + ", response content:");
        System.out.println(httpResponse.getStatusLine());
        System.out.println(bodySubscribe);
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
