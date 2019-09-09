package api.domain.params.jssdk;

import api.domain.params.BaseParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author NEWCIH
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class GetTicketParam extends BaseParam {

    /**
     * 取值为 wx_card 或 jsapi
     */
    private String type;

}
