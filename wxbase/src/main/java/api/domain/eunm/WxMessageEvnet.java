package api.domain.eunm;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * 微信推送事件类型枚举
 *
 * @author NEWCIH
 */
public enum WxMessageEvnet {

    EVENT_SUBSCRIBE("subscribe"), EVENT_UNSUBSCRIBE("unsubscribe"), EVENT_SCAN("SCAN"), EVENT_LOCATION("LOCATION"), EVENT_CLICK("CLICK"), EVENT_VIEW("VIEW");

    private String value;

    WxMessageEvnet(String v) {
        this.value = v;
    }

    public static WxMessageEvnet get(String value) {
        Optional<WxMessageEvnet> evnetOptional = Stream.of(values()).parallel().filter(v -> value.equals(v.value)).findFirst();
        return evnetOptional.orElse(null);
    }

    @Override
    public String toString() {
        return this.value;
    }

    public static void main(String[] args) {
        System.out.println(WxMessageEvnet.valueOf("EVENT_UNSUBSCRIBE"));
    }
}
