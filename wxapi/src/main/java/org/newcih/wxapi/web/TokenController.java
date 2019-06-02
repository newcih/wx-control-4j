package org.newcih.wxapi.web;

import api.domain.params.common.TokenParam;
import org.newcih.wxapi.domain.WxDataInfo;
import org.newcih.wxapi.domain.response.Response;
import org.newcih.wxapi.service.impl.CommonServiceImpl;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author NEWCIH
 */
@RestController
public class TokenController extends BaseWxController {

    private final CommonServiceImpl commonService;

    public TokenController(CommonServiceImpl commonService) {
        this.commonService = commonService;
    }

    /**
     * 获取AccessToken
     *
     * @param param  WEB请求传入参数
     * @param wxInfo AOP自动注入参数
     * @return
     */
    @PostMapping("accesstoken/get")
    public Response getAccessToken(@RequestBody @Valid TokenParam param, WxDataInfo wxDataInfo) {
        return Response.success(wxDataInfo.getWxInfo().getAccessToken());
    }

}
