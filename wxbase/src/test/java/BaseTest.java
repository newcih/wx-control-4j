import api.domain.params.common.TokenParam;
import api.domain.response.common.TokenResponse;
import api.service.WechatRequest;
import domain.WechatInfo;
import org.junit.Before;
import org.junit.Test;
import org.junit.internal.runners.JUnit4ClassRunner;
import org.junit.runner.RunWith;

@RunWith(JUnit4ClassRunner.class)
public class BaseTest {

    private WechatInfo wechatInfo1;
    private WechatInfo wechatInfo2;

    @Before
    public void init() {
        // 创建公众号1
        wechatInfo1 = new WechatInfo();
        wechatInfo1.setAppid("XXXX");
        wechatInfo1.setAppsecret("YYYYY");
        // 创建公众号2
        wechatInfo2 = new WechatInfo();
        wechatInfo2.setAppid("XXXX");
        wechatInfo2.setAppsecret("YYYYY");
    }

    /**
     * 获取AccessToken
     */
    @Test
    public void getAccessToken() {
        TokenParam param = new TokenParam();
        System.out.println(">>>>>>>>>> " + wechatInfo1);
        param.setAppid(wechatInfo1.getAppid());
        param.setSecret(wechatInfo1.getAppsecret());

        /**
         * 当项目需要管理多个公众号时，可单独调用接口获取不同公众号的数据
         */
        TokenResponse response1 = WechatRequest.getToken.apply(param, wechatInfo1);
        TokenResponse response2 = WechatRequest.getToken.apply(param, wechatInfo2);
        assert response1 != null && response2 != null;
    }

}
