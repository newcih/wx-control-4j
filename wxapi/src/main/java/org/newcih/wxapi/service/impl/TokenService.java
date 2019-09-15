package org.newcih.wxapi.service.impl;

import api.domain.params.common.TokenParam;
import api.domain.params.jssdk.GetTicketParam;
import api.domain.response.BaseResponse;
import api.domain.response.ResponseCode;
import api.domain.response.common.TokenResponse;
import api.domain.response.jssdk.GetTicketResponse;
import api.service.WechatRequest;
import org.newcih.wxapi.domain.WechatDataInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import util.StringUtil;

import java.util.function.Predicate;

/**
 * @author newcih
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TokenService {

    private final RedisTemplate<String, String> redisTemplate;
    static final String REDIS_KEY_ACCESS_TOKEN_SUFFIX = "_access_token";
    static final String REDIS_KEY_API_TICKET_SUFFIX = "_api_ticket";
    static final String REDIS_KEY_JS_API_TICKET_SUFFIX = "_js_api_ticket";
    private static final Logger logger = LoggerFactory.getLogger(TokenService.class);

    public TokenService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 判断accessToken是否有效
     */
    public static Predicate<WechatDataInfo> accessTokenIsValid = wechatDataInfo ->
            ResponseCode.ACCESSTOKEN_INVALID.equals(WechatRequest.getCallBackIP.andThen(BaseResponse::getErrcode).apply(null, wechatDataInfo.getWechatInfo()));

    /**
     * access_token 是公众号的全局唯一接口调用凭据，公众号调用各接口时都需使用access_token。
     * api_ticket 是用于调用微信卡券JS API的临时票据，有效期为7200 秒，通过access_token 来获取。
     * jsapi_ticket 是公众号用于调用微信JS接口的临时票据。
     *
     * @param wechatDataInfo
     * @return
     */
    public void refreshToken(WechatDataInfo wechatDataInfo) {
        /**
         * 刷新access token
         */
        TokenParam tokenParam = new TokenParam(wechatDataInfo.getWechatInfo());
        TokenResponse tokenResponse = WechatRequest.getToken.apply(tokenParam, wechatDataInfo.getWechatInfo());
        if (StringUtil.notBlank.test(tokenResponse.getAccessToken())) {
            String accessToken = tokenResponse.getAccessToken();
            redisTemplate.opsForValue().set(getClass().getName() + REDIS_KEY_ACCESS_TOKEN_SUFFIX, accessToken);
        }

        /**
         * 刷新jsapi ticket
         */
        GetTicketParam jsApiTicketParam = new GetTicketParam("jsapi");
        GetTicketResponse jsApiTicketResponse = WechatRequest.getTicket.apply(jsApiTicketParam, wechatDataInfo.getWechatInfo());
        if (ResponseCode.SUCCESS.equals(jsApiTicketResponse.getErrcode())) {
            String jsApiTicket = jsApiTicketResponse.getTicket();
            redisTemplate.opsForValue().set(getClass().getName() + REDIS_KEY_JS_API_TICKET_SUFFIX, jsApiTicket);
        }

        /**
         * 刷新api ticket
         */
        GetTicketParam apiTicketParam = new GetTicketParam("wx_card");
        GetTicketResponse apiTicketResponse = WechatRequest.getTicket.apply(apiTicketParam, wechatDataInfo.getWechatInfo());
        if (ResponseCode.SUCCESS.equals(apiTicketResponse.getErrcode())) {
            String apiTicket = apiTicketResponse.getTicket();
            redisTemplate.opsForValue().set(getClass().getName() + REDIS_KEY_API_TICKET_SUFFIX, apiTicket);
        }

        logger.info("执行公众号{}的Token刷新操作，本次刷新Token -> {}, JsApiTicket -> {}, WxCardTicket -> {}", wechatDataInfo, tokenResponse.getAccessToken(), jsApiTicketResponse.getTicket(), apiTicketResponse.getTicket());
    }

}
