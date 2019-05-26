package api.domain.response.account;

import api.domain.response.BaseResponse;
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

}
