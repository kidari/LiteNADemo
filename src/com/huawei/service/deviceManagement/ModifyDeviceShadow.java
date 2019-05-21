package com.huawei.service.deviceManagement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.huawei.utils.Constant;
import com.huawei.utils.HttpsUtil;
import com.huawei.utils.JsonUtil;
import com.huawei.utils.StreamClosedHttpResponse;

/**
 * Modify Device Shadow :
 * This interface is used to modify device shadow information of a device.
 */
public class ModifyDeviceShadow {

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
        String urlModifyDeviceShadow = Constant.MODIFY_DEVICE_SHADOW + "/" + deviceId;

        //please replace the following parameter values, when you use the demo.
        //The value of serviceId must be consistent with the serviceId of profile that have been preset to IoT platform.
        //The value of desired must be consistent with the properties of profile that have been preset to IoT platform.
        String serviceId= "Temperature";
        ObjectNode desired = JsonUtil.convertObject2ObjectNode("{\"targetTemperature\":\"35\"}");
        
        Map<String, Object> ServiceDesiredDTO = new HashMap<>();
        ServiceDesiredDTO.put("serviceId", serviceId);
        ServiceDesiredDTO.put("desired", desired);
        
        List<Map<String, Object>> serviceDesireds = new ArrayList<Map<String, Object>>();
        serviceDesireds.add(ServiceDesiredDTO);
        
        Map<String, Object> paramModifyDeviceShadow = new HashMap<>();
        paramModifyDeviceShadow.put("serviceDesireds", serviceDesireds);

        String jsonRequest = JsonUtil.jsonObj2Sting(paramModifyDeviceShadow);

        Map<String, String> header = new HashMap<>();
        header.put(Constant.HEADER_APP_KEY, appId);
        header.put(Constant.HEADER_APP_AUTH, "Bearer" + " " + accessToken);

        StreamClosedHttpResponse responseModifyDeviceShadow = httpsUtil.doPutJsonGetStatusLine(urlModifyDeviceShadow,
                header, jsonRequest);

        System.out.println("ModifyDeviceShadow, response content:");
        System.out.println(responseModifyDeviceShadow.getStatusLine());
        System.out.println(responseModifyDeviceShadow.getContent());
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