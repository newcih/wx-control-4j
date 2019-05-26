package domain;

import lombok.Data;

/**
 * 公众号详细信息
 *
 * @author NEWCIH
 */
@Data
public class WxInfo {

    private String appid;
    private String appsecret;
    private String wechatId;
    private String token;
    private String encodingAesKey;
    private volatile String accessToken;
    private volatile String jsapiTicket;
    private volatile String apiTicket;

}
