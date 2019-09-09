package api.domain.eunm;

import lombok.Getter;

/**
 * 微信请求URL地址
 *
 * @author NEWCIH
 */
@Getter
public enum ApiUrlEnum {

    /**
     *
     */
    CLEAR_QUOTA("https://api.weixin.qq.com/cgi-bin/clear_quota", "清除接口调用次数", RequestMethodEnum.POST),
    /**
     *
     */
    TICKET_GET_TICKET("https://api.weixin.qq.com/cgi-bin/ticket/getticket", "获取JSSDK的ticket", RequestMethodEnum.GET),
    /**
     *
     */
    QRCODE_CREATE("https://api.weixin.qq.com/cgi-bin/qrcode/create", "创建二维码", RequestMethodEnum.POST),
    /**
     *
     */
    GET_CALLBACK_IP("https://api.weixin.qq.com/cgi-bin/getcallbackip", "获取服务器地址IP", RequestMethodEnum.GET),
    /**
     *
     */
    TOKEN("https://api.weixin.qq.com/cgi-bin/token", "获取AccessToken", RequestMethodEnum.GET);

    /**
     * 请求地址
     */
    private String url;
    /**
     * 请求名称
     */
    private String name;
    /**
     * 请求方法
     */
    private RequestMethodEnum requestMethodEnum;

    ApiUrlEnum(String url, String name, RequestMethodEnum requestMethodEnum) {
        this.url = url;
        this.name = name;
        this.requestMethodEnum = requestMethodEnum;
    }

}
