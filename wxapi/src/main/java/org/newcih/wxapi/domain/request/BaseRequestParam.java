package org.newcih.wxapi.domain.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author newcih
 */
@Data
public class BaseRequestParam {

    @NotNull(message = "APPID不能为空")
    private String appid;

    @NotNull(message = "请求签名不能为空")
    private String sign;

    @NotNull(message = "请求需带上签名用的随机字符串")
    private String random;

}
