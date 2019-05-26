package org.newcih.wxapi.service.impl;

import api.domain.eunm.ApiUrl;
import api.domain.params.common.GetCallBackIPParam;
import api.domain.params.common.TokenParam;
import api.domain.params.jssdk.GetTicketParam;
import api.domain.response.ResponseCode;
import api.domain.response.common.GetCallBackIPResponse;
import api.domain.response.common.TokenResponse;
import api.domain.response.jssdk.GetTicketResponse;
import api.web.WxRequest;
import domain.WxInfo;
import org.newcih.wxapi.service.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import util.StringUtil;

/**
 * @author NEWCIH
 */
@Service
public class CommonServiceImpl implements BaseService {

    private static final Logger log = LoggerFactory.getLogger(CommonServiceImpl.class);

    /**
     * 如果公众号基于安全等考虑，需要获知微信服务器的IP地址列表，以便进行相关限制，可以通过该接口获得微信服务器IP地址列表或者IP网段信息。
     *
     * @param wxInfo
     * @return
     */
    public GetCallBackIPResponse getIpList(WxInfo wxInfo) {
        return WxRequest.request(new GetCallBackIPParam(), ApiUrl.GET_CALLBACK_IP, wxInfo, GetCallBackIPResponse.class);
    }

    /**
     * 检测access token是否过期，如果过期，则刷新
     *
     * @param wxInfo
     */
    public void handleExpireAccessToken(WxInfo wxInfo) {
        GetCallBackIPResponse response = getIpList(wxInfo);
        if (ResponseCode.ACCESSTOKEN_INVALID.equals(response.getErrcode())) {
            refreshAccessToken(wxInfo);
            log.info("公众号{}当前access token经检测已失效，现重新获取得到", wxInfo);
        }
    }

    /**
     * 暂不支持校验js api ticket的有效性
     *
     * @param wxInfo
     */
    public void handleExpireJsApiTicket(WxInfo wxInfo) {
    }

    /**
     * 暂不支持校验api ticket的有效性
     *
     * @param wxInfo
     */
    public void handleExpireApiTicket(WxInfo wxInfo) {
    }

    /**
     * access_token是公众号的全局唯一接口调用凭据，公众号调用各接口时都需使用access_token。
     *
     * @param wxInfo
     * @return
     */
    public void refreshAccessToken(WxInfo wxInfo) {
        TokenParam param = new TokenParam(wxInfo);
        TokenResponse response = WxRequest.request(param, ApiUrl.TOKEN, wxInfo, TokenResponse.class);
        if (StringUtil.notBlank.test(response.getAccessToken())) {
            wxInfo.setAccessToken(response.getAccessToken());
        }
    }

    /**
     * 刷新api ticket
     * api_ticket 是用于调用微信卡券JS API的临时票据，有效期为7200 秒，通过access_token 来获取。
     *
     * @param wxInfo
     */
    public void refreshApiTicket(WxInfo wxInfo) {
        GetTicketParam param = new GetTicketParam();
        param.setType("wx_card");
        GetTicketResponse response = WxRequest.request(param, ApiUrl.TICKET_GET_TICKET, wxInfo, GetTicketResponse.class);
        if (ResponseCode.SUCCESS.equals(response.getErrcode())) {
            wxInfo.setApiTicket(response.getTicket());
        }
    }

    /**
     * 刷新JsApiTicket
     * jsapi_ticket是公众号用于调用微信JS接口的临时票据。
     *
     * @param wxInfo
     */
    public void refreshJsApiTicket(WxInfo wxInfo) {
        GetTicketParam param = new GetTicketParam();
        param.setType("jsapi");
        GetTicketResponse response = WxRequest.request(param, ApiUrl.TOKEN, wxInfo, GetTicketResponse.class);
        if (ResponseCode.SUCCESS.equals(response.getErrcode())) {
            wxInfo.setJsapiTicket(response.getTicket());
        }
    }

}
