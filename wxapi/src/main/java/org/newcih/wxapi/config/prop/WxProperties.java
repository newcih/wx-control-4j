package org.newcih.wxapi.config.prop;

import domain.WxInfo;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.stream.Collectors.toMap;


/**
 * @author NEWCIH
 */
@Component
@ConfigurationProperties("wxinfo")
@Data
public class WxProperties {

    private List<WxInfo> list = new ArrayList<>();

    @Getter(value = AccessLevel.PRIVATE)
    private Map<String, WxInfo> appIdMap = new ConcurrentHashMap<>();
    @Getter(value = AccessLevel.PRIVATE)
    private Map<String, WxInfo> wechatIdMap = new ConcurrentHashMap<>();

    public Map<String, WxInfo> getAppIdInnerMap() {
        if (list.size() > 0) {
            appIdMap = list.parallelStream().collect(toMap(WxInfo::getAppid, wxInfo -> wxInfo));
        }
        return appIdMap;
    }

    public Map<String, WxInfo> getWechatIdInnerMap() {
        if (list.size() > 0) {
            wechatIdMap = list.parallelStream().collect(toMap(WxInfo::getWechatId, wxInfo -> wxInfo));
        }
        return wechatIdMap;
    }

}
