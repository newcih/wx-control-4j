package api.domain.eunm;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * 微信推送事件类型枚举
 *
 * @author NEWCIH
 */
public enum WechatMessageEventEnum {

    /**
     * 关注事件
     */
    EVENT_SUBSCRIBE("subscribe"),
    /**
     * 取消关注事件
     */
    EVENT_UNSUBSCRIBE("unsubscribe"),
    /**
     * 扫描事件
     */
    EVENT_SCAN("SCAN"),
    /**
     * 定位事件
     */
    EVENT_LOCATION("LOCATION"),
    /**
     * 点击
     */
    EVENT_CLICK("CLICK"),
    /**
     * 查看事件
     */
    EVENT_VIEW("VIEW");

    private String value;

    WechatMessageEventEnum(String v) {
        this.value = v;
    }

    public static WechatMessageEventEnum get(String value) {
        Optional<WechatMessageEventEnum> evnetOptional = Stream.of(values()).parallel().filter(v -> value.equals(v.value)).findFirst();
        return evnetOptional.orElse(null);
    }

    @Override
    public String toString() {
        return this.value;
    }

    public static void main(String[] args) {
        System.out.println(WechatMessageEventEnum.valueOf("EVENT_UNSUBSCRIBE"));
    }
}
