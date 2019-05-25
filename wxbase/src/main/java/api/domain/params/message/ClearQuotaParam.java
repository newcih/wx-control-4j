package api.domain.params.message;

import api.domain.params.BaseParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author NEWCIH
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ClearQuotaParam extends BaseParam {

    private String appid;

}
