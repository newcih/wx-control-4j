package org.newcih.wxapi.dao.provider;

import domain.WechatInfo;
import org.apache.ibatis.jdbc.SQL;
import org.newcih.wxapi.domain.WechatDataInfo;
import util.StringUtil;

/**
 * @author NEWCIH
 */
public class WechatDataInfoProvider {

    public String updateToken(WechatDataInfo wxDataInfo) {
        WechatInfo wechatInfo = wxDataInfo.getWechatInfo();
        return new SQL() {{
            UPDATE("wechat_info");
            if (StringUtil.notBlank.test(wechatInfo.getAccessToken())) {
                SET("access_token = #{wechatInfo.accessToken}");
            }
            if (StringUtil.notBlank.test(wechatInfo.getJsapiTicket())) {
                SET("jsapi_ticket = #{wechatInfo.jsapiTicket}");
            }
            if (StringUtil.notBlank.test(wechatInfo.getApiTicket())) {
                SET("api_ticket = #{wechatInfo.apiTicket}");
            }
            SET("token_update_time = #{tokenUpdateTime}");
            SET("version = #{version}+1");
            WHERE("id = #{id}");
            WHERE("version = #{version}");
        }}.toString();
    }

}
