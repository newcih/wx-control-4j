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
import org.newcih.wxapi.dao.WxDataInfoMapper;
import org.newcih.wxapi.domain.WxDataInfo;
import org.newcih.wxapi.service.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import util.StringUtil;

import java.time.LocalDateTime;
import java.util.List;

import static org.newcih.wxapi.cron.SyncTokenTimer.UPDATE_TOKEN_SECOND_PERIOD;

/**
 * @author NEWCIH
 */
@Service
public class CommonServiceImpl implements BaseService {

    private static final Logger log = LoggerFactory.getLogger(CommonServiceImpl.class);

    private final WxDataInfoMapper wxDataInfoMapper;

    public CommonServiceImpl(WxDataInfoMapper wxDataInfoMapper) {
        this.wxDataInfoMapper = wxDataInfoMapper;
    }

    /**
     * 获取公众号列表
     *
     * @return
     */
    public List<WxDataInfo> listWxDataInfos() {
        return wxDataInfoMapper.list();
    }

    public WxDataInfo getWxDataInfo(Integer id) {
        return wxDataInfoMapper.get(id);
    }

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
     * @deprecated 暂时不启用
     */
    @Deprecated
    public void handleExpireAccessToken(WxInfo wxInfo) {
    }

    /**
     * 暂不支持校验js api ticket的有效性
     *
     * @param wxInfo
     * @deprecated 暂时不启用
     */
    @Deprecated
    public void handleExpireJsApiTicket(WxInfo wxInfo) {
    }

    /**
     * 暂不支持校验api ticket的有效性
     *
     * @param wxInfo
     * @deprecated 暂时不启用
     */
    @Deprecated
    public void handleExpireApiTicket(WxInfo wxInfo) {
    }

    public String getAccessTokenByAppId(String appid) {
        return wxDataInfoMapper.getAccessTokenByAppid(appid);
    }

    public WxDataInfo getByAppid(String appid) {
        return wxDataInfoMapper.getByAppid(appid);
    }

    /**
     * access_token 是公众号的全局唯一接口调用凭据，公众号调用各接口时都需使用access_token。
     * api_ticket 是用于调用微信卡券JS API的临时票据，有效期为7200 秒，通过access_token 来获取。
     * jsapi_ticket 是公众号用于调用微信JS接口的临时票据。
     *
     * @param wxDataInfo
     * @return
     */
    public boolean refreshToken(WxDataInfo wxDataInfo) {

        LocalDateTime tokenExpireTime = wxDataInfo.getTokenUpdateTime().plusSeconds(UPDATE_TOKEN_SECOND_PERIOD);
        log.info("{} - {}", wxDataInfo.getTokenUpdateTime(), UPDATE_TOKEN_SECOND_PERIOD);
        log.info("{}", tokenExpireTime);
        if (!LocalDateTime.now().isAfter(tokenExpireTime)) {
            log.info("公众号{}当前未到待更新token时间，不执行刷新操作", wxDataInfo);
            return true;
        }

        /**
         * 刷新access token
         */
        TokenParam tokenParam = new TokenParam();
        tokenParam.setAppid(wxDataInfo.getWxInfo().getAppid());
        tokenParam.setSecret(wxDataInfo.getWxInfo().getAppsecret());
        TokenResponse tokenResponse = WxRequest.request(tokenParam, ApiUrl.TOKEN, wxDataInfo.getWxInfo(), TokenResponse.class);
        if (StringUtil.notBlank.test(tokenResponse.getAccessToken())) {
            String accessToken = tokenResponse.getAccessToken();
            wxDataInfo.getWxInfo().setAccessToken(accessToken);
        }

        /**
         * 刷新jsapi ticket
         */
        GetTicketParam jsApiTicketParam = new GetTicketParam();
        jsApiTicketParam.setType("jsapi");
        GetTicketResponse jsApiTicketResponse = WxRequest.request(jsApiTicketParam, ApiUrl.TICKET_GET_TICKET, wxDataInfo.getWxInfo(), GetTicketResponse.class);
        if (ResponseCode.SUCCESS.equals(jsApiTicketResponse.getErrcode())) {
            String jsApiTicket = jsApiTicketResponse.getTicket();
            wxDataInfo.getWxInfo().setJsapiTicket(jsApiTicket);
        }

        /**
         * 刷新api ticket
         */
        GetTicketParam apiTicketParam = new GetTicketParam();
        apiTicketParam.setType("wx_card");
        GetTicketResponse apiTicketResponse = WxRequest.request(apiTicketParam, ApiUrl.TICKET_GET_TICKET, wxDataInfo.getWxInfo(), GetTicketResponse.class);
        if (ResponseCode.SUCCESS.equals(apiTicketResponse.getErrcode())) {
            String apiTicket = apiTicketResponse.getTicket();
            wxDataInfo.getWxInfo().setApiTicket(apiTicket);
        }

        wxDataInfo.setTokenUpdateTime(LocalDateTime.now());
        Integer resultNum = wxDataInfoMapper.updateToken(wxDataInfo);
        return resultNum >= 1;
    }

}
