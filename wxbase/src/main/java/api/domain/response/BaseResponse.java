package api.domain.response;

import lombok.Data;

/**
 * 微信请求基类
 *
 * @author NEWCIH
 */
@Data
public class BaseResponse {

    protected Integer errcode;
    protected String errmsg;

}
