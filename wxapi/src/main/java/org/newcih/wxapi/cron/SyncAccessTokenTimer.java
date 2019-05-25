package org.newcih.wxapi.cron;

import api.domain.eunm.ApiUrl;
import api.domain.params.common.TokenParam;
import api.domain.response.common.TokenResponse;
import api.web.WxRequest;
import org.newcih.wxapi.config.prop.WxProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import util.StringUtil;

/**
 * 刷新AccessToken定时器
 *
 * @author NEWCIH
 */
@Component
public class SyncAccessTokenTimer {

    private final static Logger log = LoggerFactory.getLogger(SyncAccessTokenTimer.class);

    private final WxProperties wxProperties;

    public SyncAccessTokenTimer(WxProperties wxProperties) {
        this.wxProperties = wxProperties;
    }

    /**
     * 每 1.5 * 60 * 60 秒刷新一次
     */
    @Scheduled(fixedRate = 15 * 6 * 60 * 1000)
    public void syncAccessToken() {
        log.info("定时器开始更新公众号AccessToken，本次待更新{}个公众号", wxProperties.getList().size());
        wxProperties.getList().forEach(wxInfo -> {
            TokenParam param = new TokenParam(wxInfo);
            TokenResponse response = new WxRequest<TokenParam>().request(param, ApiUrl.TOKEN, wxInfo, TokenResponse.class);
            if (StringUtil.notBlank.test(response.getAccessToken())) {
                wxInfo.setAccessToken(response.getAccessToken());
                System.out.println("更新成功" + wxInfo);
            } else {
                log.warn("更新公众号{}的ACCESS_TOKEN失败，状态{}，消息返回{}", wxInfo.getAppid(), response.getErrcode(), response.getErrmsg());
            }
        });
    }

}
