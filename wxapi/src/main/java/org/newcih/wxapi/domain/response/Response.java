package org.newcih.wxapi.domain.response;

import lombok.Data;

/**
 * 默认Web请求返回对象
 *
 * @author NEWCIH
 */
@Data
public class Response {

    private String msg;
    private Integer code;
    private Object data;

    public static Response success(Object body) {
        Response r = new Response();
        r.setCode(OK);
        r.setData(body);
        return r;
    }

    public static Response fail(String msg) {
        Response r = new Response();
        r.setCode(INTERNAL_ERROR);
        r.setMsg(msg);
        return r;
    }

    public static final int OK = 200;
    public static final int INTERNAL_ERROR = 500;
}
