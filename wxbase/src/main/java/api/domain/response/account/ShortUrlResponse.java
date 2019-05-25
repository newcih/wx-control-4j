package api.domain.response.account;

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
public class ShortUrlResponse extends BaseResponse {

    @SerializedName("short_url")
    private String shortUrl;

    @Override
    public ShortUrlResponse jsonObjectToResponse(JsonObject response) {
        super.jsonObjectToResponse(response);
        this.setShortUrl(response.get("short_url").getAsString());
        return this;
    }

}
