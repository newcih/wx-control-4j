package org.newcih.wxapi.cron;

import org.newcih.wxapi.config.prop.WxProperties;
import org.newcih.wxapi.service.impl.CommonServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 刷新Token定时器，主要刷新如下几类数据
 * <p>
 * 1、access token
 * 2、jsapi ticket
 * 3、jssdk ticket
 *
 * @author NEWCIH
 */
@Component
public class SyncTokenTimer {

    private final static Logger log = LoggerFactory.getLogger(SyncTokenTimer.class);

    private final WxProperties wxProperties;
    private final CommonServiceImpl commonService;
    private final static Marker TIMERMARKER = MarkerFactory.getMarker("Timer");

    public SyncTokenTimer(WxProperties wxProperties, CommonServiceImpl commonService) {
        this.wxProperties = wxProperties;
        this.commonService = commonService;
    }

    /**
     * 定时器刷新AccessToken
     * access_token的有效期目前为2个小时，需定时刷新，重复获取将导致上次获取的access_token失效。
     * <p>
     * 每 1.5 * 60 * 60 秒刷新一次
     */
    @Scheduled(fixedRate = 15 * 6 * 60 * 1000L)
    public void syncAccessToken() {
        log.info(TIMERMARKER, "定时器开始更新公众号AccessToken，本次待更新{}个公众号", wxProperties.getList().size());
        wxProperties.getList().parallelStream().forEach(commonService::refreshAccessToken);
    }

    /**
     * 定时器刷新JsTicket
     * jsapi_ticket是公众号用于调用微信JS接口的临时票据。正常情况下，jsapi_ticket的有效期为7200秒，通过access_token来获取。
     * 由于获取jsapi_ticket的api调用次数非常有限，频繁刷新jsapi_ticket会导致api调用受限，影响自身业务，开发者必须在自己的服务全局缓存jsapi_ticket 。
     */
    @Scheduled(fixedRate = 15 * 6 * 60 * 1000L, initialDelay = 100L)
    public void syncJsTicket() {
        log.info(TIMERMARKER, "定时器开始更新公众号JsApiTicket，本次待更新{}个公众号", wxProperties.getList().size());
        wxProperties.getList().parallelStream().forEach(commonService::refreshJsApiTicket);
    }

    /**
     * 定时器刷新ApiTicket
     * api_ticket 是用于调用微信卡券JS API的临时票据，有效期为7200 秒，通过access_token 来获取。
     */
    @Scheduled(fixedRate = 15 * 6 * 60 * 1000L, initialDelay = 100L)
    public void syncApiTicket() {
        log.info(TIMERMARKER, "定时器开始更新公众号ApiTicket，本次待更新{}个公众号", wxProperties.getList().size());
        wxProperties.getList().parallelStream().forEach(commonService::refreshApiTicket);
    }

    /**
     * 定时器检测Token有效性
     */
//    @Scheduled(fixedRate = 10 * 1000L, initialDelay = 200L)
//    public void checkToken() {
//        log.info(TIMERMARKER, "开始定时检测AccessToken，Ticket等有效性");
//        wxProperties.getList().parallelStream().forEach(commonService::handleExpireAccessToken);
//        log.info(TIMERMARKER, "定时检测AccessToken，Ticket等有效性完成");
//    }

}
