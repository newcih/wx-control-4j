package org.newcih.wxapi.service;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import org.newcih.wxapi.domain.request.BaseRequestParam;
import org.newcih.wxapi.exception.SignWrongException;
import org.springframework.stereotype.Service;

/**
 * @author NEWCIH
 */
@Service
public class BaseService {

    /**
     * 验证接口请求签名
     *
     * @param param
     */
    public void checkSign(BaseRequestParam param) throws SignWrongException {
        if (param == null || StrUtil.isEmpty(param.getSign()) || StrUtil.isEmpty(param.getAppid())) {
            throw new NullPointerException("接口请求不可缺少Appid或签名数据");
        }

        String appid = param.getAppid();
        String random = param.getRandom();

        String md5 = new String(DigestUtil.md5(appid + random, "UTF-8"));
        if (!md5.equals(param.getSign())) {
            throw new SignWrongException("接口签名错误");
        }
    }
}
