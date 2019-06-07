package org.newcih.wxapi.web;

import api.domain.params.common.TokenParam;
import api.domain.params.jssdk.GetTicketParam;
import org.apache.ibatis.annotations.ResultMap;
import org.newcih.wxapi.domain.WxDataInfo;
import org.newcih.wxapi.domain.request.BaseRequestParam;
import org.newcih.wxapi.domain.response.Response;
import org.newcih.wxapi.service.impl.CommonServiceImpl;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author NEWCIH
 */
@RestController
@RequestMapping("token")
public class TokenController extends BaseWxController {

    private final CommonServiceImpl commonService;

    public TokenController(CommonServiceImpl commonService) {
        this.commonService = commonService;
    }

    /**
     * 获取AccessToken
     *
     * @param param      WEB请求传入参数
     * @param wxDataInfo AOP自动注入参数
     * @return
     */
    @PostMapping("accessToken")
    public Response getAccessToken(@RequestBody @Valid BaseRequestParam param, WxDataInfo wxDataInfo) {
        return Response.success(wxDataInfo.getWxInfo().getAccessToken());
    }

    @PostMapping("apiTicket")
    public Response getApiTicket(@RequestBody @Valid BaseRequestParam param, WxDataInfo wxDataInfo) {
        return Response.success(wxDataInfo.getWxInfo().getApiTicket());
    }

    @PostMapping("jsapiTicket")
    public Response getJsApiTicket(@RequestBody @Valid BaseRequestParam param, WxDataInfo wxDataInfo) {
        return Response.success(wxDataInfo.getWxInfo().getJsapiTicket());
    }
}
