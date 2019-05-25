# 项目说明

本项目基于对微信公众号开发的一点知识，开发一个微信公众号的中控服务。即实现多公众号管理，统一获取AccessToken、JsapiTicket，接入微信服务器消息推送等的项目。  

## 项目开发介绍

### 使用技术介绍

+ 基于JDK 8+
+ 基于SpringBoot 2.x
+ 无需数据库配置

### 项目结构介绍

+ **wxbase**
    基本的微信公众号接口调用支持
    
    + src/main/java/api 对微信公众号接口的简单封装
        + domain
            + enum 常用枚举变量，如接口请求地址等
            + params 微信接口请求参数实体，根据公众号开发文档目录结构创建包结构
            + response 微信接口请求结果实体，根据公众号开发文档目录结构创建包结构
            + WxMessageEntity 微信服务器消息实体，用于接收微信消息推送
        + service
            + GetWxResponseService 封装微信请求Service层
        + web
            + WxRequest 封装微信请求Web层
    + src/main/java/domain 微信公众号信息实体
    
+ **wxapi**
    调用wxbase封装的接口的Demo实现，实现自服务的功能，如接口获取access token
    
    + src/main/java/org/newcih/wxapi
        + config 常用配置
            + aop 常用切面实现，如签名验证，微信WxInfo对象数据填充
            + prop 常用变量注入，如微信公众号数据列表，从配置文件注入
        + cron 定时器实现，如Access Token获取
        + service 常用Service
            + message 微信消息推送对应的事件处理服务实现
            + impl 常用Service实现
        + web 对外接口
        
# 开始开发

## 项目数据配置

启动项目前，需要先配置一些微信公众号数据到wxapi模块的application.yaml，配置见示例

## WxBase模块接入示例

+ 调用微信接口获取AccessToken
```java
import api.domain.eunm.ApiUrl;
import api.domain.params.common.TokenParam;
import api.domain.response.common.TokenResponse;
import api.web.WxRequest;
import domain.WxInfo;
import org.junit.Before;
import org.junit.Test;
import org.junit.internal.runners.JUnit4ClassRunner;
import org.junit.runner.RunWith;

@RunWith(JUnit4ClassRunner.class)
public class BaseTest {

    /**
    * 微信公众号信息实体
    */
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
        // 所有微信请求通过WxRequest发起，传入相应的入参和响应对象即可实现对应的微信请求
        // 这里获取AccessToken的Demo有两处地方传入wxInfo，实际是因为获取AccessToken的接口比较特殊
        // 该微信接口的入参需要带入secret，所以TokenParam需要WxInfo的数据
        // 所有WxRequest的请求调用都需要wxinfo对象，用于提供accessToken数据
        // 在wxapi模块里，wxinfo对象的accesstoken通过定时器写入
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

# 后续开发进展

+ 接入微信服务器地址配置，以消息队列的形式分发多公众号的微信消息推送
+ 丰富更多接口封装