package org.newcih.wxapi.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author NEWCIH
 */
@RestController
public class BaseWechatController {

    /**
     * 签名密钥
     */
    @Value("${sign-key}")
    protected String signKey;

}
