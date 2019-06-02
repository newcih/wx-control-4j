package org.newcih.wxapi.web;

import api.domain.params.account.CreateQrCodeParam;
import org.newcih.wxapi.domain.WxDataInfo;
import org.newcih.wxapi.domain.response.Response;
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
    public Response createQrCode(@RequestBody CreateQrCodeParam param, WxDataInfo wxDataInfo) {
        return Response.success(accountService.createQrCode(param, wxDataInfo));
    }

}
