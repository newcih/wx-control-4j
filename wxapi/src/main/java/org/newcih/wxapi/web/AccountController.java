package org.newcih.wxapi.web;

import api.domain.params.account.CreateQrCodeParam;
import domain.WxInfo;
import org.newcih.wxapi.domain.response.DefaultResponse;
import org.newcih.wxapi.service.impl.AccountServiceImpl;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author NEWCIH
 */
@RestController
@RequestMapping("account")
public class AccountController extends BaseWxController {

    private final AccountServiceImpl accountService;

    public AccountController(AccountServiceImpl accountService) {
        this.accountService = accountService;
    }

    @PostMapping("createQrCode")
    public DefaultResponse createQrCode(@RequestBody CreateQrCodeParam param, WxInfo wxInfo) {
        return DefaultResponse.success(accountService.createQrCode(param, wxInfo));
    }

}
