package api.domain.response.common;

import api.domain.response.BaseResponse;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author NEWCIH
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class TokenResponse extends BaseResponse {
    @SerializedName("access_token")
    private String accessToken;
    @SerializedName("expires_in")
    private Integer expiresIn;

    @Override
    public TokenResponse jsonObjectToResponse(JsonObject params) {
        super.jsonObjectToResponse(params);
        this.setAccessToken(params.get("access_token").getAsString());
        this.setExpiresIn(params.get("expires_in").getAsInt());
        return this;
    }
}
