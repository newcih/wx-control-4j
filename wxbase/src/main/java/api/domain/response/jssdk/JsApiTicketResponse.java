package api.domain.response.jssdk;

import api.domain.response.BaseResponse;
import com.google.gson.JsonObject;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author NEWCIH
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ToString(callSuper = true)
public class JsApiTicketResponse extends BaseResponse {

    private String ticket;
    private Integer expiresIn;

    @Override
    public JsApiTicketResponse jsonObjectToResponse(JsonObject params) {
        super.jsonObjectToResponse(params);
        this.setExpiresIn(params.get("expires_in").getAsInt());
        this.setTicket(params.get("ticket").getAsString());
        return this;
    }
}
