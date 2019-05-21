package com.huawei.service.subscribtionManagement;

import java.util.HashMap;
import java.util.Map;

import com.huawei.utils.Constant;
import com.huawei.utils.HttpsUtil;
import com.huawei.utils.JsonUtil;
import com.huawei.utils.StreamClosedHttpResponse;

/**
 * Delete Batch Subscriptions :
 * This interface is used to delete subscriptions in batch.
 */
public class DeleteBatchSubscriptions {

    public static void main(String args[]) throws Exception {

        // Two-Way Authentication
        HttpsUtil httpsUtil = new HttpsUtil();
        httpsUtil.initSSLConfigForTwoWay();

        // Authentication，get token
        String accessToken = login(httpsUtil);
        
        //Please make sure that the following parameter values have been modified in the Constant file.
        String appId = Constant.APPID;
        String urlDeleteBatchSubscriptions = Constant.DELETE_BATCH_SUBSCRIPTIONS;
        
        /*
         * please replace the subscription type, when you use the demo.
         * service Notify Type:
         * deviceAdded|bindDevice|deviceInfoChanged|deviceDataChanged|deviceDatasChanged|
         * deviceDeleted|messageConfirm|commandRsp|deviceEvent|serviceInfoChanged|
         * ruleEvent|deviceModelAdded|deviceModelDeleted|
         * deviceDesiredPropertiesModifyStatusChanged
         * management Notify Type:
         * swUpgradeStateChangeNotify|swUpgradeResultNotify|fwUpgradeStateChangeNotify|fwUpgradeResultNotify
         */
        String notifyType = "swUpgradeStateChangeNotify";
        
        Map<String, String> paramDeleteBatchSubscriptions = new HashMap<>();
        paramDeleteBatchSubscriptions.put("notifyType", notifyType);
        
        Map<String, String> header = new HashMap<>();
        header.put(Constant.HEADER_APP_KEY, appId);
        header.put(Constant.HEADER_APP_AUTH, "Bearer" + " " + accessToken);

        StreamClosedHttpResponse responseDeleteBatchSubscriptions = httpsUtil.doDeleteWithParasGetStatusLine(
        		urlDeleteBatchSubscriptions, paramDeleteBatchSubscriptions, header);

        System.out.println("DeleteBatchSubscriptions, response content:");
        System.out.println(responseDeleteBatchSubscriptions.getStatusLine());
        System.out.println(responseDeleteBatchSubscriptions.getContent());
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
