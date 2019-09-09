package api.domain.params.common;

import api.domain.params.BaseParam;
import com.google.gson.annotations.SerializedName;
import domain.WechatInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author NEWCIH
 */
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
public class TokenParam extends BaseParam {

    private String appid;
    private String secret;
    @SerializedName("grant_type")
    private String grantType = "client_credential";

    public TokenParam(WechatInfo wechatInfo) {
        this.appid = wechatInfo.getAppid();
        this.secret = wechatInfo.getAppsecret();
    }
}
