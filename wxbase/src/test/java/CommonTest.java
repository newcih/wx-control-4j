import api.domain.eunm.ApiUrl;
import api.domain.params.common.GetCallBackIPParam;
import api.domain.params.common.TokenParam;
import api.domain.params.message.ClearQuotaParam;
import api.domain.response.common.GetCallBackIPResponse;
import api.domain.response.common.TokenResponse;
import api.web.WxRequest;
import com.google.gson.JsonObject;
import domain.WxInfo;
import org.junit.Before;
import org.junit.Test;

public class CommonTest extends BaseTest {

    private WxInfo wxInfo;

    @Before
    public void init() {
        wxInfo = new WxInfo();
        wxInfo.setAppid("wx476a377901cdda1c");
        wxInfo.setAppsecret("188be0760b6ce2eeece0012b1b2632d7");
    }

    @Test
    public void refreshAccessToken() {


        for (int i = 0; i < 20; i++) {
            TokenParam param = new TokenParam(wxInfo);
            TokenResponse response = WxRequest.request(param, ApiUrl.TOKEN, wxInfo, TokenResponse.class);
            System.out.println(response);
            wxInfo.setAccessToken(response.getAccessToken());

            ClearQuotaParam p = new ClearQuotaParam();
            p.setAppid(wxInfo.getAppid());
            JsonObject j = WxRequest.request(p, ApiUrl.CLEAR_QUOTA, wxInfo);
            System.out.println(j);

            GetCallBackIPResponse response1 = WxRequest.request(new GetCallBackIPParam(), ApiUrl.GET_CALLBACK_IP, wxInfo, GetCallBackIPResponse.class);
            System.out.println(response1);
        }

    }
}
