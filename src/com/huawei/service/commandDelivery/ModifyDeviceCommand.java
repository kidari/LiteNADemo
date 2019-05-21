package com.huawei.service.commandDelivery;

import java.util.HashMap;
import java.util.Map;

import com.huawei.utils.Constant;
import com.huawei.utils.HttpsUtil;
import com.huawei.utils.JsonUtil;
import com.huawei.utils.StreamClosedHttpResponse;

/**
 * Modify Device Command :
 * This interface is used to modify the device command information.
 * Currently only supports to modify such device Command that whose status is PENDING.
 */
public class ModifyDeviceCommand {

	public static void main(String args[]) throws Exception {

        // Two-Way Authentication
        HttpsUtil httpsUtil = new HttpsUtil();
        httpsUtil.initSSLConfigForTwoWay();

        // Authentication，get token
        String accessToken = login(httpsUtil);

        //Please make sure that the following parameter values have been modified in the Constant file.
		String appId = Constant.APPID;

        // please replace the commandId, when you use the demo.
        String commandId = "aa0413242212451aa08fd53c1693807a";
        String urlModifyDeviceCommand = Constant.MODIFY_DEVICE_COMMAND + "/" + commandId;

        //Currently only supports Modify the status of device command from PENDING to CANCELED.
        String status = "CANCELED";
        
        Map<String, String> paraModifyDeviceCommand = new HashMap<>();
        paraModifyDeviceCommand.put("status", status);
        
        String jsonRequest = JsonUtil.jsonObj2Sting(paraModifyDeviceCommand);
                
        Map<String, String> header = new HashMap<>();
        header.put(Constant.HEADER_APP_KEY, appId);
        header.put(Constant.HEADER_APP_AUTH, "Bearer" + " " + accessToken);
        
        StreamClosedHttpResponse responseModifyDeviceCommand = httpsUtil.doPutJsonGetStatusLine(urlModifyDeviceCommand, header, jsonRequest);
        
        System.out.println("UpdateAsynCommand, response content:");
		System.out.println(responseModifyDeviceCommand.getStatusLine());
		System.out.println(responseModifyDeviceCommand.getContent());
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
