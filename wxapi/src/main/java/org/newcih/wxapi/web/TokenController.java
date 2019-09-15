package org.newcih.wxapi.web;

import org.newcih.wxapi.domain.request.BaseRequestParam;
import org.newcih.wxapi.domain.response.Response;
import org.newcih.wxapi.exception.SignWrongException;
import org.newcih.wxapi.service.impl.WechatInfoService;
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

    private final WechatInfoService wechatInfoService;

    public TokenController(WechatInfoService wechatInfoService) {
        this.wechatInfoService = wechatInfoService;
    }

    /**
     * 获取AccessToken
     *
     * @param param      WEB请求传入参数
     * @param wxDataInfo AOP自动注入参数
     * @return
     */
    @PostMapping("accessToken")
    public Response getAccessToken(@RequestBody @Valid BaseRequestParam param) throws SignWrongException {
        wechatInfoService.checkSign(param);
        String accessToken = wechatInfoService.getAccessTokenByAppId(param.getAppid());
        return Response.success(accessToken);
    }

    @PostMapping("apiTicket")
    public Response getApiTicket(@RequestBody @Valid BaseRequestParam param) throws SignWrongException {
        wechatInfoService.checkSign(param);
        String apiTicket = wechatInfoService.getApiTicketByAppId(param.getAppid());
        return Response.success(apiTicket);
    }

    @PostMapping("jsapiTicket")
    public Response getJsApiTicket(@RequestBody @Valid BaseRequestParam param) throws SignWrongException {
        wechatInfoService.checkSign(param);
        String jsApiTicket = wechatInfoService.getJsApiTicketByAppId(param.getAppid());
        return Response.success(jsApiTicket);
    }
}
