package api.web;

import api.domain.eunm.ApiUrl;
import api.domain.params.BaseParam;
import api.domain.response.BaseResponse;
import api.service.GetWxResponseService;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import domain.WxInfo;

/**
 * 通用微信请求
 *
 * @param <K>
 * @author NEWCIH
 */
public class WxRequest<K extends BaseParam> {

    /**
     * 微信请求
     *
     * @param params
     * @param apiUrl
     * @param wxInfo
     * @param vClass
     * @param <V>
     * @return
     */
    public <V extends BaseResponse> V request(K params, ApiUrl apiUrl, WxInfo wxInfo, Class<V> vClass) {
        JsonObject jsonObject = GetWxResponseService.request(params, apiUrl, wxInfo);
        return new Gson().fromJson(jsonObject, vClass);
    }

}
