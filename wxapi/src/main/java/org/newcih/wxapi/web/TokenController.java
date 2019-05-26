package org.newcih.wxapi.web;

import api.domain.params.common.TokenParam;
import domain.WxInfo;
import org.newcih.wxapi.domain.response.DefaultResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author NEWCIH
 */
@RestController
public class TokenController extends BaseWxController {

    /**
     * 获取AccessToken
     *
     * @param param  WEB请求传入参数
     * @param wxInfo AOP自动注入参数
     * @return
     */
    @PostMapping("accesstoken/get")
    public DefaultResponse getAccessToken(@RequestBody @Valid TokenParam param, WxInfo wxInfo) {
        return DefaultResponse.success(wxInfo.getAccessToken());
    }

}
