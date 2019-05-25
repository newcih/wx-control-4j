import api.domain.eunm.ApiUrl;
import api.domain.params.account.CreateQrCodeParam;
import api.domain.params.common.TokenParam;
import api.domain.response.account.CreateQrCodeResponse;
import api.domain.response.common.TokenResponse;
import api.web.WxRequest;
import domain.WxInfo;
import org.junit.Before;
import org.junit.Test;
import org.junit.internal.runners.JUnit4ClassRunner;
import org.junit.runner.RunWith;

@RunWith(JUnit4ClassRunner.class)
public class BaseTest {

    private WxInfo wxInfo;

    @Before
    public void init() {
        wxInfo = new WxInfo();
        wxInfo.setAppid("XXXX");
        wxInfo.setAppsecret("YYYYY");
    }

    /**
     * 获取AccessToken
     */
    @Test
    public void getAccessToken() {
        TokenParam param = new TokenParam();
        param.setAppid(wxInfo.getAppid());
        param.setSecret(wxInfo.getAppsecret());
        TokenResponse response = new WxRequest<TokenParam>().request(param, ApiUrl.TOKEN, wxInfo, TokenResponse.class);
        System.out.println(response);

        // 或者
        param = new TokenParam(wxInfo);
        response = new WxRequest<TokenParam>().request(param, ApiUrl.TOKEN, wxInfo, TokenResponse.class);
        System.out.println(response);
    }

    /**
     * 创建带参数的二维码
     */
    @Test
    public void createQrCode() {
        CreateQrCodeParam param = new CreateQrCodeParam();
        param.setActionName("XXXX");
        param.setActionInfo(null);

        CreateQrCodeResponse response = new WxRequest<CreateQrCodeParam>().request(param, ApiUrl.QRCODE_CREATE, wxInfo, CreateQrCodeResponse.class);
        System.out.println(response);
    }

}
