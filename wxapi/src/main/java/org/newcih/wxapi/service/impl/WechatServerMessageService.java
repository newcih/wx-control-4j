package org.newcih.wxapi.service.impl;

import api.domain.WechatMessageEntity;
import com.qq.weixin.mp.aes.AesException;
import com.qq.weixin.mp.aes.WXBizMsgCrypt;
import org.newcih.wxapi.domain.WechatDataInfo;
import org.newcih.wxapi.util.XMLParse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

import static org.newcih.wxapi.util.XmlUtil.xmlBodyToString;

/**
 * @author newcih
 */
@Service
public class WechatServerMessageService {

    private final WechatInfoService wechatInfoService;
    private static final Logger logger = LoggerFactory.getLogger(WechatServerMessageService.class);

    public WechatServerMessageService(WechatInfoService wechatInfoService) {
        this.wechatInfoService = wechatInfoService;
    }

    /**
     * 从微信服务器消息中解析出消息封装对象
     *
     * @param request
     * @return
     * @throws AesException
     */
    public WechatMessageEntity decryptMessage(HttpServletRequest request) throws AesException, IOException {
        // 查询该消息所属公众号
        String xmlString = xmlBodyToString(request);
        WechatDataInfo wechatDataInfo = getWechatInfoFromServerMessage(xmlString);

        if (wechatDataInfo == null) {
            logger.warn("微信消息{}尚未找到公众号信息", xmlString);
            return null;
        }

        String nonce = request.getParameter("nonce");
        String msgSignature = request.getParameter("msg_signature");
        String timeStamp = request.getParameter("timestamp");
        WXBizMsgCrypt wxBizMsgCrypt =
                new WXBizMsgCrypt(wechatDataInfo.getWechatInfo().getToken(), wechatDataInfo.getWechatInfo().getEncodingAesKey(),
                        wechatDataInfo.getWechatInfo().getAppid());
        String message = wxBizMsgCrypt.decryptMsg(msgSignature, timeStamp, nonce, xmlString);
        Map<String, String> result = XMLParse.extract(message);
        return new WechatMessageEntity(result, wechatDataInfo.getWechatInfo());
    }

    /**
     * 从微信服务器消息获取公众号信息
     *
     * @param xmlString
     * @return
     */
    public WechatDataInfo getWechatInfoFromServerMessage(String xmlString) {
        WechatDataInfo result = null;
        try {
            Map<String, String> getWechatIdMap = XMLParse.extract(xmlString);
            String wechatId = getWechatIdMap.get("ToUserName");
            result = wechatInfoService.getWechatInfoByWechatId(wechatId);
        } catch (Exception e) {
            logger.error("解析微信服务器消息发生异常", e);
        }
        return result;
    }
}
