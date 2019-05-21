package com.huawei.service.deviceManagement;

import com.huawei.utils.Constant;
import com.huawei.utils.HttpsUtil;
import com.huawei.utils.JsonUtil;
import com.huawei.utils.StreamClosedHttpResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * Remove Indirect Connected Device :
 * This interface is used to remove indirect connected device.
 */
public class RemoveIndirectConnectedDevice {

    public static void main(String args[]) throws Exception {

        // Two-Way Authentication
        HttpsUtil httpsUtil = new HttpsUtil();
        httpsUtil.initSSLConfigForTwoWay();

        // Authentication��get token
        String accessToken = login(httpsUtil);

        //Please make sure that the following parameter values have been modified in the Constant file.
        String appId = Constant.APPID;
        String deviceId = "0492bf00-41ca-4f6f-bdfa-409daacd0004";

        //please replace the following parameter values, when you use the demo.
        //And those parameter values must be consistent with the content of profile that have been preset to IoT platform.
        String serviceId = "Remove";
        String mode = "ACK";
        String method = "REMOVE";
        String toType = "GATEWAY";

        //please replace the callbackURL, when you use the demo.
        String callbackURL = "http://server:port/na/iocm/message/confirm";

        String urlRemoveIndirectConnectedDevice = Constant.REMOVE_INDIRECT_CONNECTED_DEVICE;
        urlRemoveIndirectConnectedDevice =String.format(urlRemoveIndirectConnectedDevice, deviceId, serviceId);

        Map<String, String> commandNA2CloudHeader = new HashMap<>();
        commandNA2CloudHeader.put("mode", mode);
        commandNA2CloudHeader.put("method", method);
        commandNA2CloudHeader.put("toType", toType);
        commandNA2CloudHeader.put("callbackURL", callbackURL);

        Map<String, String> commandNA2CloudBody = new HashMap<>();
        commandNA2CloudBody.put("cmdBody", "remove indirect device cmd body content.");

        Map<String, Object> paramRemoveIndirectConnectedDevice = new HashMap<>();
        paramRemoveIndirectConnectedDevice.put("header", commandNA2CloudHeader);
        paramRemoveIndirectConnectedDevice.put("body", commandNA2CloudBody);

        Map<String, String> header = new HashMap<>();
        header.put(Constant.HEADER_APP_KEY, appId);
        header.put(Constant.HEADER_APP_AUTH, "Bearer" + " " + accessToken);

        String jsonRequest = JsonUtil.jsonObj2Sting(paramRemoveIndirectConnectedDevice);

        StreamClosedHttpResponse responseRemoveIndirectConnectedDevice = httpsUtil
                .doPostJsonGetStatusLine(urlRemoveIndirectConnectedDevice, header, jsonRequest);


        System.out.println("RemoveIndirectConnectedDevice, response content:");
        System.out.println(responseRemoveIndirectConnectedDevice.getStatusLine());
        System.out.println(responseRemoveIndirectConnectedDevice.getContent());
        System.out.println();

    }

    /**
     * Authentication��get token
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