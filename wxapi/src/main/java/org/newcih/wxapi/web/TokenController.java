package org.newcih.wxapi.web;

import org.newcih.wxapi.domain.WechatDataInfo;
import org.newcih.wxapi.domain.request.BaseRequestParam;
import org.newcih.wxapi.domain.response.Response;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author NEWCIH
 */
@RestController
@RequestMapping("token")
public class TokenController extends BaseWechatController {

    /**
     * 获取AccessToken
     *
     * @param param      WEB请求传入参数
     * @param wxDataInfo AOP自动注入参数
     * @return
     */
    @PostMapping("accessToken")
    public Response getAccessToken(@RequestBody @Valid BaseRequestParam param, WechatDataInfo wxDataInfo) {
        return Response.success(wxDataInfo.getWechatInfo().getAccessToken());
    }

    @PostMapping("apiTicket")
    public Response getApiTicket(@RequestBody @Valid BaseRequestParam param, WechatDataInfo wxDataInfo) {
        return Response.success(wxDataInfo.getWechatInfo().getApiTicket());
    }

    @PostMapping("jsapiTicket")
    public Response getJsApiTicket(@RequestBody @Valid BaseRequestParam param, WechatDataInfo wxDataInfo) {
        return Response.success(wxDataInfo.getWechatInfo().getJsapiTicket());
    }
}
