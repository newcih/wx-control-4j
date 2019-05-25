package api.domain.params.account;

import api.domain.params.BaseParam;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Map;

/**
 * 生成带参数二维码请求参数
 *
 * @author NEWCIH
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CreateQrCodeParam extends BaseParam {

    @SerializedName("expire_seconds")
    private Integer expireSeconds;
    @SerializedName("action_name")
    private String actionName;
    @SerializedName("action_info")
    private ActionInfo actionInfo;

    @Data
    public static class ActionInfo {
        private Map<String, String> scene;
    }

}

