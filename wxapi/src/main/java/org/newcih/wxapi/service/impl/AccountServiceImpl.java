package org.newcih.wxapi.service.impl;

import api.domain.eunm.ApiUrl;
import api.domain.params.account.CreateQrCodeParam;
import api.domain.response.account.CreateQrCodeResponse;
import api.web.WxRequest;
import org.newcih.wxapi.domain.WxDataInfo;
import org.newcih.wxapi.service.BaseService;
import org.springframework.stereotype.Service;

/**
 * @author NEWCIH
 */
@Service
public class AccountServiceImpl implements BaseService {

    public CreateQrCodeResponse createQrCode(CreateQrCodeParam param, WxDataInfo wxDataInfo) {
        return WxRequest.request(param, ApiUrl.QRCODE_CREATE, wxDataInfo.getWxInfo(), CreateQrCodeResponse.class);
    }

}
