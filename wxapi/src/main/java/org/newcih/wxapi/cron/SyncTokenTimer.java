package org.newcih.wxapi.cron;

import org.newcih.wxapi.domain.WechatDataInfo;
import org.newcih.wxapi.service.LockService;
import org.newcih.wxapi.service.impl.TokenService;
import org.newcih.wxapi.service.impl.WechatInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import util.NetworkUtil;

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

    private final static Logger logger = LoggerFactory.getLogger(SyncTokenTimer.class);
    public final static Long UPDATE_TOKEN_SECOND_PERIOD = 60 * 60L;
    private final static Integer MAX_RETRY_TIME = 3;
    private TokenService tokenService;
    private WechatInfoService wechatInfoService;
    private LockService lockService;

    public SyncTokenTimer(TokenService tokenService, WechatInfoService wechatInfoService, LockService lockService) {
        this.tokenService = tokenService;
        this.wechatInfoService = wechatInfoService;
        this.lockService = lockService;
    }

    /**
     * 定时器刷新AccessToken
     * access_token的有效期目前为2个小时，需定时刷新，重复获取将导致上次获取的access_token失效。
     * 每1.5小时执行一次
     * <p>
     */
    @Scheduled(fixedRate = 6 * 1000L)
    public void syncAccessToken() {
        // 使用Redis构建分布式锁，避免多机情况下多次调用定时器
        if (!lockService.lock(SyncTokenTimer.class.getName())) {
            logger.info("本机{}获取定时器分布式锁失败，本机不执行定时器", NetworkUtil.getLocalIp());
            return;
        } else {
            logger.info("本机{}获取定时器分布式锁成功，本机执行定时器", NetworkUtil.getLocalIp());
        }

        List<WechatDataInfo> wxDataInfoList = wechatInfoService.listWechatDataInfos();
        logger.info("定时器开始更新公众号AccessToken，本次待更新{}个公众号", wxDataInfoList.size());
        wxDataInfoList.parallelStream()
                .forEach(wxDataInfo -> {
                    boolean updateFlag = tokenService.refreshToken(wxDataInfo);
                    int currentRetryTime = 3;
                    while (!updateFlag && (currentRetryTime++) < MAX_RETRY_TIME) {
                        logger.info("公众号{}定时器刷新token写入数据库失败，进行重试", wxDataInfo);
                        WechatDataInfo temp = wechatInfoService.getWechatDataInfo(wxDataInfo.getId());
                        updateFlag = tokenService.refreshToken(temp);
                    }
                });
    }

}
