# 项目说明

本项目基于对微信公众号开发的一点知识，开发一个微信公众号的中控服务。即实现多公众号管理，统一获取AccessToken、JsapiTicket，接入微信服务器消息推送等的项目。  

## 项目开发介绍

### 项目优点

+ 开箱即用
+ 定时检测AccessToken等固定有效期的token或ticket的有效性，确保不会因其它平台刷新造成失效
+ 单机部署可避免数据库配置，确保便捷和高可用
+ 支持负载均衡运行，和项目安全重启不中断 (如果要设置负载均衡运行，则需要开启数据库配置) [**暂未实现，目前支持单机无数据库部署**]
+ 支持多公众号服务器地址配置，接收多公众号的微信消息推送，使用消息队列广播发布形式，支持多个子项目订阅相关公众号的消息推送

### 使用技术介绍

+ 基于JDK 8+
+ 基于SpringBoot 2.x
+ 无需数据库配置

### 项目结构介绍

+ **wxbase**
    基本的微信公众号接口调用支持
    
    + *src/main/java/api* 对微信公众号接口的简单封装
        + **domain**
            + **enum** 常用枚举变量，如接口请求地址等
            + **params** 微信接口请求参数实体，根据公众号开发文档目录结构创建包结构
            + **response** 微信接口请求结果实体，根据公众号开发文档目录结构创建包结构
            + **WxMessageEntity** 微信服务器消息实体，用于接收微信消息推送
        + **service**
            + **GetWxResponseService** 封装微信请求Service层
        + **web**
            + **WxRequest** 封装微信请求Web层
    + *src/main/java/domain* 微信公众号信息实体
    
> 参数类命名规则： 取微信接口的URL路径的动作单词，拼接上Param或Response。举例: **https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=wx_card** 是获取微信卡券ticket的接口，则对应的请求参数Param类和Response类的命名则是GetTicketParam和GetTicketResponse
    
+ **wxapi**
    调用wxbase封装的接口的Demo实现，实现自服务的功能，如接口获取access token
    
    + *src/main/java/org/newcih/wxapi*
        + **config** 常用配置
            + **aop** 常用切面实现，如签名验证，微信WxInfo对象数据填充
            + **prop** 常用变量注入，如微信公众号数据列表，从配置文件注入
        + **cron** 定时器实现，如Access Token获取
        + **service** 常用Service
            + **message** 微信消息推送对应的事件处理服务实现
            + **impl** 常用Service实现
        + **web** 对外接口
        
# 开始开发

## 项目数据配置

启动项目前，需要先配置一些微信公众号数据到wxapi模块的application.yaml，配置见示例

```yaml
server:
    port: 2400

spring:
    application:
        name: 微信中控服务
#  datasource:
#    druid:
#      username: root
#      password: 12345q
#      url: jdbc:mysql://localhost:3306/wx_control?useUnicode=true&characterEncoding=utf-8&useSSL=false
#      driver-class-name: com.mysql.cj.jdbc.Driver
#      initial-size: 10
#      max-active: 50
#      min-idle: 10
#      max-wait: 60000
#      time-between-eviction-runs-millis: 60000
#      min-evictable-idle-time-millis: 300000
#      validation-query: select 1 from dual
#      test-while-idle: true
#mybatis:
#  type-aliases-package: org.newcih.wxapi.domain

# 微信配置
wxinfo:
    #  accessTokenRefreshSecond: 5400
    # 公众号列表
    list:
        - {appid: 'your appid 1', appsecret: 'your appsecret 1', wechatId: 'your wechatId 1', token: '', encodingAesKey: ''}
        - {appid: 'your appid 2', appsecret: 'your appsecret 2', wechatId: 'your wechatId 2', token: '', encodingAesKey: ''}
        - {appid: 'your appid 3', appsecret: 'your appsecret 3', wechatId: 'your wechatId 3', token: '', encodingAesKey: ''}

# 日志配置
logging:
    level:
        root: info
    pattern:
        console: '[%d{yyyy-MM-dd HH:mm:ss.sss }] [%p] %marker %m%n'

```

### 关于项目配置的FAQ

+ 为什么公众号列表那里需要配置wechatId呢？这是什么？
> wechatId即是微信号，跟用户微信号一样，公众号也有微信号，但这个微信号并不是在公众号平台里面设置的微信号，平台里面设置的微信号是显示用的。实际上公众号的微信号是微信固定生成的，格式如 gh_46dXXXXXX4c9。配置这个是因为当需要启用该项目作为公众号服务器地址配置时，微信推送的消息只会带有公众号的微信号，而不是appid。所以在项目的配置文件上加入wechatId，就可以实现多公众号配置同一个服务器地址，同时又能区分出微信推送的消息属于哪个公众号。


## WxBase模块接入示例

+ 调用微信接口获取AccessToken
```java
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
```

## 启动项目

+ 打包
```bash
mvn clean package -Dmaven.test.skip=true 
```

+ 启动
```bash
mvn spring-boot:run
```      

+ 开启Swagger接口
```bash
http://localhost:2400/swagger-ui.html
```

+ 使用示例
```http request

POST http://localhost:2400/accesstoken/get?appid=your appid
Content-Type: application/json

# 该请求用户获取access token

```

# 微信开发

## 一些备注

+ access token 过期
> 为确保接口调用正常，在微信接口调用发生错误，如AccessToken过期时，推荐重新获取access token，并重新发起接口调用。确保任何一次微信请求都能正常发起，而不会因其它平台刷新了access token后造成access token失效。另外，微信声称5分钟内，旧AccessToken仍有效，这个时间间隔仍有待确定，因为实际测试中，发现旧AccessToken在重新刷新并使用了新AccessToken调用微信接口后，旧AccessToken的有效期并不能保持5分钟。  

+ 微信网页授权回调地址配置多个
> 微信公众号网页授权回调地址，目前可配置2个。但可能还是会遇到其它项目需要配置而无法实现的问题。这时候常有人支持使用get-weixin-code.html的配置方式来支持，允许任意个项目能正常使用网页授权。这个虽然允许任何人将回调地址填写成自己的地址来接收code，但微信还是留了一手，有code不行，还需要带上secret去拿access token。所以get-weixin-code没有想象中那么不安全。

# 后续开发进展

+ 接入微信服务器地址配置，以消息队列的形式分发多公众号的微信消息推送
+ 丰富更多接口封装