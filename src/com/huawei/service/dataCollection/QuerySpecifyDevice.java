package com.huawei.service.dataCollection;

import java.util.HashMap;
import java.util.Map;

import com.huawei.utils.Constant;
import com.huawei.utils.HttpsUtil;
import com.huawei.utils.JsonUtil;
import com.huawei.utils.StreamClosedHttpResponse;

/**
 * Query Specify Device :
 * This interface is used to query information for specify device.
 */
public class QuerySpecifyDevice {

    public static void main(String args[]) throws Exception {

        // Two-Way Authentication
        HttpsUtil httpsUtil = new HttpsUtil();
        httpsUtil.initSSLConfigForTwoWay();

        // Authentication，get token
        String accessToken = login(httpsUtil);

        //Please make sure that the following parameter values have been modified in the Constant file.
        String appId = Constant.APPID;

        //please replace the deviceId, when you use the demo.
        String deviceId = "0be6a1d4-1a0e-410a-bf57-7d81cf31da5a";
        String urlQuerySpecifyDevice = Constant.QUERY_SPECIFY_DEVICE + "/" + deviceId;

        Map<String, String> paramQuerySpecifyDevice = new HashMap<>();
        paramQuerySpecifyDevice.put("appId", appId);

        Map<String, String> header = new HashMap<>();
        header.put(Constant.HEADER_APP_KEY, appId);
        header.put(Constant.HEADER_APP_AUTH, "Bearer" + " " + accessToken);
        
        StreamClosedHttpResponse bodyQuerySpecifyDevice = httpsUtil.doGetWithParasGetStatusLine(urlQuerySpecifyDevice,
        		paramQuerySpecifyDevice, header);

        System.out.println("QuerySpecifyDevice, response content:");
        System.out.println(bodyQuerySpecifyDevice.getStatusLine());
        System.out.println(bodyQuerySpecifyDevice.getContent());
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
