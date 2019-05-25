package org.newcih.wxapi.domain.response;

import lombok.Data;

/**
 * 默认Web请求返回对象
 *
 * @author NEWCIH
 */
@Data
public class DefaultResponse {

    private String msg;
    private Integer code;
    private Object data;

    public static DefaultResponse success = new DefaultResponse();
    public static DefaultResponse fail = new DefaultResponse();

    static {
        success.setCode(200);
    }

    public static DefaultResponse success(Object data) {
        DefaultResponse defaultResponse = new DefaultResponse();
        defaultResponse.setCode(200);
        defaultResponse.setData(data);
        return defaultResponse;
    }

    public static DefaultResponse fail(String msg) {
        DefaultResponse defaultResponse = new DefaultResponse();
        defaultResponse.setMsg(msg);
        defaultResponse.setCode(400);
        return defaultResponse;
    }

}
