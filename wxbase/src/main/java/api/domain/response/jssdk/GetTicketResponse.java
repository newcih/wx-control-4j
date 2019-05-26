package api.domain.response.jssdk;

import api.domain.response.BaseResponse;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author NEWCIH
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class GetTicketResponse extends BaseResponse {

    private String ticket;
    @SerializedName("expires_in")
    private Integer expiresIn;

}
