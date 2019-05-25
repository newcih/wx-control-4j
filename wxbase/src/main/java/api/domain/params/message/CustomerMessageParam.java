package api.domain.params.message;

import api.domain.params.BaseParam;
import com.google.gson.JsonObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 自定义消息请求参数
 *
 * @author NEWCIH
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CustomerMessageParam extends BaseParam {

    private JsonObject messageData;

}
