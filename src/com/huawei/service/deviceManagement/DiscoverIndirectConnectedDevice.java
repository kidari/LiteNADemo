package com.huawei.service.deviceManagement;

import com.huawei.utils.Constant;
import com.huawei.utils.HttpsUtil;
import com.huawei.utils.JsonUtil;
import com.huawei.utils.StreamClosedHttpResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * Discover Indirect Connected Device :
 * This interface is used to discover and add indirect device to a gateway.
 */
public class DiscoverIndirectConnectedDevice {

    public static void main(String args[]) throws Exception {

        // Two-Way Authentication
        HttpsUtil httpsUtil = new HttpsUtil();
        httpsUtil.initSSLConfigForTwoWay();

        // Authentication��get token
        String accessToken = login(httpsUtil);

        //Please make sure that the following parameter values have been modified in the Constant file.
        String appId = Constant.APPID;

        // please replace the deviceId and serviceId, when you use the demo.
        String deviceId = "9e620731-9a8b-42b7-b685-263546b74afc";

        //please replace the following parameter values, when you use the demo.
        //And those parameter values must be consistent with the content of profile that have been preset to IoT platform.
        String serviceId = "Discovery";
        String mode = "ACK";
        String method = "DISCOVERY";
        String toType = "GATEWAY";

        //please replace the callbackURL, when you use the demo.
        String callbackURL = "http://server:port/na/iocm/message/confirm";

        String urlDiscoverIndirectConnectedDevice = Constant.DISCOVER_INDIRECT_CONNECTED_DEVICE;
        urlDiscoverIndirectConnectedDevice = String.format(urlDiscoverIndirectConnectedDevice, deviceId, serviceId);

        Map<String, String> commandNA2CloudHeader = new HashMap<>();
        commandNA2CloudHeader.put("mode", mode);
        commandNA2CloudHeader.put("method", method);
        commandNA2CloudHeader.put("toType", toType);
        commandNA2CloudHeader.put("callbackURL", callbackURL);

        Map<String, String> commandNA2CloudBody = new HashMap<>();
        commandNA2CloudBody.put("cmdBody", "discover indirect device cmd body content.");

        Map<String, Object> paramDiscoverIndirectConnectedDevice = new HashMap<>();
        paramDiscoverIndirectConnectedDevice.put("header", commandNA2CloudHeader);
        paramDiscoverIndirectConnectedDevice.put("body", commandNA2CloudBody);

        Map<String, String> header = new HashMap<>();
        header.put(Constant.HEADER_APP_KEY, appId);
        header.put(Constant.HEADER_APP_AUTH, "Bearer" + " " + accessToken);

        String jsonRequest = JsonUtil.jsonObj2Sting(paramDiscoverIndirectConnectedDevice);

        StreamClosedHttpResponse responseDiscoverIndirectConnectedDevice = httpsUtil
                .doPostJsonGetStatusLine(urlDiscoverIndirectConnectedDevice, header, jsonRequest);


        System.out.println("DiscoverIndirectConnectedDevice, response content:");
        System.out.println(responseDiscoverIndirectConnectedDevice.getStatusLine());
        System.out.println(responseDiscoverIndirectConnectedDevice.getContent());
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


