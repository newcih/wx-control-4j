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
 * @author NEWCIH
 */
public class WxRequest {

    private WxInfo wxInfo;

    public WxRequest(WxInfo wxInfo) {
        this.wxInfo = wxInfo;
    }

    public WxRequest() {
    }

    /**
     * 微信请求，适用于单次调用接口的情况，这里会封装请求结果JSON数据为封装类
     *
     * @param param  请求参数
     * @param apiUrl 接口枚举
     * @param wxInfo 微信公众号信息
     * @param vClass 请求结果类型
     * @return 请求结果封装对象
     */
    public static <V extends BaseResponse, K extends BaseParam> V request(K param, ApiUrl apiUrl, WxInfo wxInfo, Class<V> vClass) {
        if (wxInfo == null) {
            throw new NullPointerException("公众号对象WxInfo参数不能为空");
        }
        JsonObject jsonObject = GetWxResponseService.request(param, apiUrl, wxInfo);
        return new Gson().fromJson(jsonObject, vClass);
    }

    /**
     * 重载，创建WxRequest对象时赋值WxInfo，便于单个公众号信息调用多次或多个接口的情况，这里会封装请求结果JSON数据为封装类
     *
     * @param param
     * @param apiUrl
     * @param vClass
     * @param <V>
     * @param <K>
     * @return
     */
    public <V extends BaseResponse, K extends BaseParam> V request(K param, ApiUrl apiUrl, Class<V> vClass) {
        if (wxInfo == null) {
            throw new NullPointerException("公众号对象WxInfo未初始化");
        }
        JsonObject jsonObject = GetWxResponseService.request(param, apiUrl, wxInfo);
        return new Gson().fromJson(jsonObject, vClass);
    }

    /**
     * 重载，创建WxRequest对象时赋值WxInfo，便于单个公众号信息调用多次或多个接口的情况，这里不会封装请求结果JSON数据为封装类
     *
     * @param param
     * @param apiUrl
     * @param <K>
     * @return
     */
    public <K extends BaseParam> JsonObject request(K param, ApiUrl apiUrl) {
        if (wxInfo == null) {
            throw new NullPointerException("公众号对象WxInfo未初始化");
        }
        return GetWxResponseService.request(param, apiUrl, wxInfo);
    }

    /**
     * 重载，适用于单次调用公众号接口的情况，这里不会封装返回的JSON数据为相应封装类
     *
     * @param param
     * @param apiUrl
     * @param wxInfo
     * @param <K>
     * @return
     */
    public static <K extends BaseParam> JsonObject request(K param, ApiUrl apiUrl, WxInfo wxInfo) {
        if (wxInfo == null) {
            throw new NullPointerException("公众号对象WxInfo参数不能为空");
        }
        return GetWxResponseService.request(param, apiUrl, wxInfo);
    }

}
