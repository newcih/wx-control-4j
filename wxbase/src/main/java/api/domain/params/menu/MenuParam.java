package api.domain.params.menu;

import api.domain.params.BaseParam;
import com.google.gson.JsonObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 自定义菜单参数
 *
 * @author NEWCIH
 * @date 2019年5月8号
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class MenuParam extends BaseParam {

    private JsonObject menuJson;

}
