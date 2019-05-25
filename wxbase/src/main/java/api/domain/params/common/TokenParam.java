package api.domain.params.common;

import api.domain.params.BaseParam;
import com.google.gson.annotations.SerializedName;
import domain.WxInfo;
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

    private String secret;
    @SerializedName("grant_type")
    private String grantType = "client_credential";

    public TokenParam(WxInfo wxInfo) {
        super.appid = wxInfo.getAppid();
        this.secret = wxInfo.getAppsecret();
    }
}
