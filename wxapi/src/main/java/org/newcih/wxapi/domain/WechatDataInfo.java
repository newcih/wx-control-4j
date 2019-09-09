package org.newcih.wxapi.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import domain.WechatInfo;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * @author NEWCIH
 */
@Data
@ToString(callSuper = true)
public class WechatDataInfo extends BaseEntity {

    private static final long serialVersionUID = 4286559151477533398L;
    private WechatInfo wechatInfo;
    private String status;
    private Integer version = 0;
    private String name;
    private LocalDateTime tokenUpdateTime = LocalDateTime.MIN;

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null) {
            return false;
        }

        if (object instanceof WechatDataInfo) {
            WechatDataInfo temp = ((WechatDataInfo) object);
            return temp.wechatInfo != null && wechatInfo != null &&
                    (temp.wechatInfo.getAppid().equals(wechatInfo.getAppid()));
        }

        return false;
    }
}
