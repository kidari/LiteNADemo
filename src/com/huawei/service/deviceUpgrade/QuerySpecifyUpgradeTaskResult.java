package com.huawei.service.deviceUpgrade;

import java.util.HashMap;
import java.util.Map;

import com.huawei.utils.Constant;
import com.huawei.utils.HttpsUtil;
import com.huawei.utils.JsonUtil;
import com.huawei.utils.StreamClosedHttpResponse;

/**
 * Query Specify Upgrade Task Result :
 * This interface is used to query the result of a upgrade task.
 */
public class QuerySpecifyUpgradeTaskResult {

	public static void main(String args[]) throws Exception {

        // Two-Way Authentication
        HttpsUtil httpsUtil = new HttpsUtil();
        httpsUtil.initSSLConfigForTwoWay();

        // Authentication，get token
        String accessToken = login(httpsUtil);

        //please replace the operationId, when you use the demo.
        String operationId = "5c00ad65f7b537019b440a4a";
        
        //Please make sure that the following parameter values have been modified in the Constant file.
		String appId = Constant.APPID;
        String urlQuerySpecifyUpgradeTaskResult = Constant.QUERY_SPECIFY_UPGRADE_TASK_RESULT + "/" + operationId;

        Map<String, String> header = new HashMap<>();
        header.put(Constant.HEADER_APP_KEY, appId);
        header.put(Constant.HEADER_APP_AUTH, "Bearer" + " " + accessToken);

        StreamClosedHttpResponse responseQuerySpecifyUpgradeTaskResult = httpsUtil.doGetWithParasGetStatusLine(urlQuerySpecifyUpgradeTaskResult, null, header);

        System.out.println("QuerySpecifyUpgradeTaskResult, response content:");
        System.out.println(responseQuerySpecifyUpgradeTaskResult.getStatusLine());
        System.out.println(responseQuerySpecifyUpgradeTaskResult.getContent());
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
