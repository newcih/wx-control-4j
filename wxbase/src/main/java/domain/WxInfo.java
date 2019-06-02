package domain;

import lombok.Data;

import java.io.Serializable;

/**
 * 公众号详细信息
 *
 * @author NEWCIH
 */
@Data
public class WxInfo implements Serializable {

    private static final long serialVersionUID = 6349959859571679754L;
    private String appid;
    private String appsecret;
    private String wechatId;
    private String token;
    private String encodingAesKey;
    private volatile String accessToken;
    private volatile String jsapiTicket;
    private volatile String apiTicket;

}
