package api.service;

import api.domain.eunm.ApiUrl;
import api.domain.eunm.RequestMethod;
import api.domain.params.BaseParam;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import domain.WxInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpUtil;

/**
 * 调用微信接口服务类
 *
 * @author NEWCIH
 */
public class GetWxResponseService {

    private final static Logger log = LoggerFactory.getLogger(GetWxResponseService.class);

    /**
     * 根据APIURL获取请求类型
     *
     * @param wxInfo 公众号参数
     * @param param  请求参数
     * @param apiurl 接口信息
     * @return JSON对象请求结果
     */
    public static JsonObject request(BaseParam param, ApiUrl apiurl, WxInfo wxInfo) {
        String url = apiurl.toString() + "?access_token=" + wxInfo.getAccessToken();
        if (RequestMethod.POST.equals(apiurl.getRequestMethod())) {
            return postRequest(param, url);
        }
        return getRequest(param, url);
    }

    /**
     * GET微信接口
     *
     * @param param 请求参数
     * @param url   接口地址
     * @return JSON对象请求结果
     */
    private static JsonObject getRequest(BaseParam param, String url) {
        if (param == null) {
            return HttpUtil.getWithJSON(null, url);
        }
        String str = new Gson().toJson(param);
        JsonElement jsonElement = new JsonParser().parse(str);
        JsonObject paramJson = jsonElement.getAsJsonObject();
        return HttpUtil.getWithJSON(paramJson, url);
    }

    /**
     * POST微信接口
     *
     * @param param 请求参数
     * @param url   接口地址
     * @return JSON对象请求结果
     */
    private static JsonObject postRequest(BaseParam param, String url) {
        if (param == null) {
            return HttpUtil.postWithJSON(null, url);
        }
        String str = new Gson().toJson(param);
        return HttpUtil.postWithJSON(str, url);
    }

}
