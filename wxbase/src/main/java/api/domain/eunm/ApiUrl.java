package api.domain.eunm;

/**
 * 微信请求URL地址
 *
 * @author NEWCIH
 */
public enum ApiUrl {

    MENU_CREATE("https://api.weixin.qq.com/cgi-bin/menu/create", RequestMethod.POST),
    MENU_GET("https://api.weixin.qq.com/cgi-bin/menu/get", RequestMethod.GET),
    MENU_DELETE("https://api.weixin.qq.com/cgi-bin/menu/delete", RequestMethod.GET),
    CLEAR_QUOTA("https://api.weixin.qq.com/cgi-bin/clear_quota", RequestMethod.POST),
    TICKET_GET_TICKET("https://api.weixin.qq.com/cgi-bin/ticket/getticket", RequestMethod.GET),
    MESSAGE_CUSTOM_SEND("https://api.weixin.qq.com/cgi-bin/message/custom/send", RequestMethod.POST),
    QRCODE_CREATE("https://api.weixin.qq.com/cgi-bin/qrcode/create", RequestMethod.POST),
    TOKEN("https://api.weixin.qq.com/cgi-bin/token", RequestMethod.GET);

    private String url;
    private RequestMethod requestMethod;

    public RequestMethod getRequestMethod() {
        return requestMethod;
    }

    ApiUrl(String s, RequestMethod requestMethod) {
        this.url = s;
        this.requestMethod = requestMethod;
    }

    @Override
    public String toString() {
        return url;
    }

}
