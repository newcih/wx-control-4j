package api.service;

import api.domain.eunm.ApiUrlEnum;
import api.domain.eunm.RequestMethodEnum;
import api.domain.params.BaseParam;
import api.domain.response.BaseResponse;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import domain.WechatInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpUtil;

/**
 * 通用微信请求
 * 封装微信公众号请求
 *
 * @author NEWCIH
 */
public abstract class BaseWechatRequest {

    private static final Logger logger = LoggerFactory.getLogger(BaseWechatRequest.class);

    public BaseWechatRequest() {
    }

    /**
     * 根据APIURL获取请求类型
     *
     * @param wechatInfo 公众号参数
     * @param param      请求参数
     * @param apiurl     接口信息
     * @return JSON对象请求结果
     */
    private static JsonObject commonRequest(BaseParam param, ApiUrlEnum apiurl, WechatInfo wechatInfo) {
        if (wechatInfo == null) {
            throw new NullPointerException("公众号数据对象WechatInfo为空");
        }
        String url = apiurl.getUrl().toString() + "?access_token=" + wechatInfo.getAccessToken();
        if (RequestMethodEnum.POST.equals(apiurl.getRequestMethodEnum())) {
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
        JsonObject result = HttpUtil.getWithJSON(paramJson, url);
        logger.info("GET -> 使用参数{}请求响应结果{}", paramJson, result);
        return result;
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
        JsonObject result = HttpUtil.postWithJSON(str, url);
        logger.info("POST -> 使用参数{}请求响应结果{}", str, result);
        return result;
    }

    /**
     * 微信请求，适用于单次调用接口的情况，这里会封装请求结果JSON数据为封装类
     *
     * @param param      请求参数
     * @param apiUrl     接口枚举
     * @param wechatInfo 微信公众号信息
     * @param vClass     请求结果类型
     * @return 请求结果封装对象
     */
    public static <V extends BaseResponse, K extends BaseParam> V request(K param, ApiUrlEnum apiUrl, WechatInfo wechatInfo, Class<V> vClass) {
        JsonObject jsonObject = commonRequest(param, apiUrl, wechatInfo);
        return new Gson().fromJson(jsonObject, vClass);
    }

    /**
     * 重载，适用于单次调用公众号接口的情况，这里不会封装返回的JSON数据为相应封装类
     *
     * @param param
     * @param apiUrl
     * @param wechatInfo
     * @param <K>
     * @return
     */
    public static <K extends BaseParam> JsonObject request(K param, ApiUrlEnum apiUrl, WechatInfo wechatInfo) {
        return commonRequest(param, apiUrl, wechatInfo);
    }

}
