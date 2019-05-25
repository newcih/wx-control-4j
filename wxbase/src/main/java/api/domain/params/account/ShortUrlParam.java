package api.domain.params.account;

import api.domain.params.BaseParam;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author NEWCIH
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ShortUrlParam extends BaseParam {

    private String action = "long2short";
    @SerializedName("long_url")
    private String longUrl;

}
