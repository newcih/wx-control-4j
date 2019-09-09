import domain.WechatInfo;
import org.junit.Before;
import org.junit.Test;
import org.junit.internal.runners.JUnit4ClassRunner;
import org.junit.runner.RunWith;

@RunWith(JUnit4ClassRunner.class)
public class CommonTest {

    private WechatInfo wechatInfo;

    @Before
    public void init() {
        wechatInfo = new WechatInfo();
        wechatInfo.setAppid("wx476a377901cdda1c");
        wechatInfo.setAppsecret("188be0760b6ce2eeece0012b1b2632d7");
    }

    @Test
    public void test() {
        System.out.println(wechatInfo);
    }
}
