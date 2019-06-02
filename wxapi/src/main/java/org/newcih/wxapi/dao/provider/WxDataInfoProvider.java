package org.newcih.wxapi.dao.provider;

import domain.WxInfo;
import org.apache.ibatis.jdbc.SQL;
import org.newcih.wxapi.domain.WxDataInfo;
import util.StringUtil;

/**
 * @author NEWCIH
 */
public class WxDataInfoProvider {

    public String updateToken(WxDataInfo wxDataInfo) {
        WxInfo wxInfo = wxDataInfo.getWxInfo();
        return new SQL() {{
            UPDATE("wx_info");
            if (StringUtil.notBlank.test(wxInfo.getAccessToken())) {
                SET("access_token = '" + wxInfo.getAccessToken() + "'");
            }
            if (StringUtil.notBlank.test(wxInfo.getJsapiTicket())) {
                SET("jsapi_ticket = '" + wxInfo.getJsapiTicket() + "'");
            }
            if (StringUtil.notBlank.test(wxInfo.getApiTicket())) {
                SET("api_ticket = '" + wxInfo.getApiTicket() + "'");
            }
            SET("token_update_time = '" + wxDataInfo.getTokenUpdateTime() + "'");
            SET("version = " + (wxDataInfo.getVersion() + 1));
            WHERE("id = " + wxDataInfo.getId());
            WHERE("version = " + wxDataInfo.getVersion());
        }}.toString();
    }

}
