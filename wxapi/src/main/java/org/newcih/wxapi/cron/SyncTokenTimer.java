package org.newcih.wxapi.cron;

import org.newcih.wxapi.domain.WxDataInfo;
import org.newcih.wxapi.service.impl.CommonServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

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
    public final static Long UPDATE_TOKEN_SECOND_PERIOD = 60 * 60L;
    private final static Integer MAX_RETRY_TIME = 3;
    private final CommonServiceImpl commonService;
    private final static Marker TIMERMARKER = MarkerFactory.getMarker("TIMER");

    public SyncTokenTimer(CommonServiceImpl commonService) {
        this.commonService = commonService;
    }

    /**
     * 定时器刷新AccessToken
     * access_token的有效期目前为2个小时，需定时刷新，重复获取将导致上次获取的access_token失效。
     * 每1.5小时执行一次
     * <p>
     */
    @Scheduled(fixedRate = 15 * 6 * 60 * 1000L)
    public void syncAccessToken() {
        List<WxDataInfo> wxDataInfoList = commonService.listWxDataInfos();
        log.info(TIMERMARKER, "定时器开始更新公众号AccessToken，本次待更新{}个公众号", wxDataInfoList.size());
        wxDataInfoList.parallelStream()
                .peek(System.out::println)
                .forEach(wxDataInfo -> {
                    boolean updateFlag = commonService.refreshToken(wxDataInfo);
                    int currentRetryTime = 3;
                    while (!updateFlag && (currentRetryTime++) < MAX_RETRY_TIME) {
                        log.info(TIMERMARKER, "公众号{}定时器刷新token写入数据库失败，进行重试", wxDataInfo);
                        WxDataInfo temp = commonService.getWxDataInfo(wxDataInfo.getId());
                        updateFlag = commonService.refreshToken(temp);
                    }
                    log.info(TIMERMARKER, "公众号{}完成token数据定时器刷新", wxDataInfo);
                });
    }

}
