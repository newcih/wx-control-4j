package api.domain;

import api.domain.eunm.WechatMessageEventEnum;
import domain.WechatInfo;
import lombok.Data;
import util.StringUtil;

import java.io.Serializable;
import java.util.Map;

/**
 * 微信消息推送参数实体
 *
 * @author NEWCIH
 */
@Data
public class WechatMessageEntity implements Serializable {

    private static final long serialVersionUID = 5477754815518494814L;

    private String appid;
    private String toUserName;
    private String fromUserName;
    private String createTime;
    private String msgType;
    private WechatMessageEventEnum wechatMessageEventEnum;
    private String eventKey;
    private String ticket;
    private String latitude;
    private String encrypt;
    private String content;
    private String msgId;
    private String longitude;
    private String mediaId;
    private String picUrl;
    private String locationY;
    private String locationX;
    private String label;
    private String scale;
    private String precision;
    private String description;
    private String title;
    private String url;

    public WechatMessageEntity(Map<String, String> data, WechatInfo wechatInfo) {
        this.setAppid(wechatInfo.getAppid());
        this.setToUserName(data.get("ToUserName"));
        this.setFromUserName(data.get("FromUserName"));
        this.setCreateTime(data.get("CreateTime"));
        this.setMsgType(data.get("MsgType"));
        if (StringUtil.notBlank.test(data.get("Event"))) {
            this.setWechatMessageEventEnum(WechatMessageEventEnum.get(data.get("Event")));
        }
        this.setMediaId(data.get("MediaId"));
        this.setPicUrl(data.get("PicUrl"));
        this.setEncrypt(data.get("Encrypt"));
        this.setEventKey(data.get("EventKey"));
        this.setTicket(data.get("Ticket"));
        this.setLatitude(data.get("Latitude"));
        this.setLongitude(data.get("Longitude"));
        this.setPrecision(data.get("Precision"));
        this.setMsgId(data.get("MsgId"));
        this.setContent(data.get("Content"));
        this.setLocationX(data.get("Location_X"));
        this.setLocationY(data.get("Location_Y"));
        this.setLabel(data.get("Label"));
        this.setScale(data.get("Scale"));
        this.setDescription(data.get("Description"));
        this.setTitle(data.get("Title"));
        this.setUrl(data.get("Url"));
    }
}
