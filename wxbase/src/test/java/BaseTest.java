import api.domain.eunm.ApiUrl;
import api.domain.params.account.CreateQrCodeParam;
import api.domain.params.common.TokenParam;
import api.domain.response.account.CreateQrCodeResponse;
import api.domain.response.common.TokenResponse;
import api.web.WxRequest;
import com.google.gson.JsonObject;
import domain.WxInfo;
import org.junit.Before;
import org.junit.Test;
import org.junit.internal.runners.JUnit4ClassRunner;
import org.junit.runner.RunWith;

@RunWith(JUnit4ClassRunner.class)
public class BaseTest {

    private WxInfo wxInfo1;
    private WxInfo wxInfo2;

    @Before
    public void init() {
        // 创建公众号1
        wxInfo1 = new WxInfo();
        wxInfo1.setAppid("XXXX");
        wxInfo1.setAppsecret("YYYYY");
        // 创建公众号2
        wxInfo2 = new WxInfo();
        wxInfo1.setAppid("XXXX");
        wxInfo1.setAppsecret("YYYYY");
    }

    /**
     * 获取AccessToken
     */
    @Test
    public void getAccessToken() {
        TokenParam param = new TokenParam();
        param.setAppid(wxInfo1.getAppid());
        param.setSecret(wxInfo1.getAppsecret());

        /**
         * 当项目需要管理多个公众号时，可单独调用接口获取不同公众号的数据
         */

        // 多公众号调用，返回封装类
        TokenResponse response1 = WxRequest.request(param, ApiUrl.TOKEN, wxInfo1, TokenResponse.class);
        TokenResponse response2 = WxRequest.request(param, ApiUrl.TOKEN, wxInfo2, TokenResponse.class);
        assert response1 != null && response2 != null;

        // 或者多公众号调用，返回原生JSON数据对象
        JsonObject jsonObject1 = WxRequest.request(param, ApiUrl.TOKEN, wxInfo1);
        JsonObject jsonObject2 = WxRequest.request(param, ApiUrl.TOKEN, wxInfo2);
        assert jsonObject1 != null && jsonObject2 != null;

        /**
         * 当项目只有一个公众号，或方法块里只需要调用一个公众号数据时
         */

        // 单公众号请求，返回封装类
        WxRequest wxRequest = new WxRequest(wxInfo1);
        response1 = wxRequest.request(param, ApiUrl.TOKEN, TokenResponse.class);
        assert response1 != null;

        // 单公众号请求，返回原生JSON数据对象
        jsonObject1 = wxRequest.request(param, ApiUrl.TOKEN);
        assert jsonObject1 != null;

    }

    /**
     * 创建带参数的二维码
     */
    @Test
    public void createQrCode() {
        CreateQrCodeParam param = new CreateQrCodeParam();
        param.setActionName("XXXX");
        param.setActionInfo(null);

        CreateQrCodeResponse response = WxRequest.request(param, ApiUrl.QRCODE_CREATE, wxInfo1, CreateQrCodeResponse.class);
        assert response != null;

        JsonObject jsonObject = WxRequest.request(param, ApiUrl.QRCODE_CREATE, wxInfo1);
        assert jsonObject != null;
    }

}
