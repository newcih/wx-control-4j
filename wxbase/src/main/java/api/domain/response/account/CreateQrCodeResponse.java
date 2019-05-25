package api.domain.response.account;

import api.domain.response.BaseResponse;
import com.google.gson.JsonObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author NEWCIH
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CreateQrCodeResponse extends BaseResponse {

    private String ticket;
    private Integer expireSeconds;
    private String url;

    @Override
    public CreateQrCodeResponse jsonObjectToResponse(JsonObject params) {
        super.jsonObjectToResponse(params);
        this.setTicket(params.get("ticket").getAsString());
        this.setExpireSeconds(params.get("expire_seconds").getAsInt());
        this.setUrl(params.get("url").getAsString());
        return this;
    }

}
