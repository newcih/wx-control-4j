package org.newcih.wxapi.service.impl;

import api.domain.eunm.ApiUrl;
import api.domain.params.account.CreateQrCodeParam;
import api.domain.response.account.CreateQrCodeResponse;
import api.web.WxRequest;
import domain.WxInfo;
import org.newcih.wxapi.service.BaseService;
import org.springframework.stereotype.Service;

/**
 * @author NEWCIH
 */
@Service
public class AccountServiceImpl implements BaseService {

    public CreateQrCodeResponse createQrCode(CreateQrCodeParam param, WxInfo wxInfo) {
        return new WxRequest().request(param, ApiUrl.QRCODE_CREATE, wxInfo, CreateQrCodeResponse.class);
    }

}
