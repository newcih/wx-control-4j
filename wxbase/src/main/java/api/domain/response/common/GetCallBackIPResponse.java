package api.domain.response.common;

import api.domain.response.BaseResponse;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author NEWCIH
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class GetCallBackIPResponse extends BaseResponse {

    @SerializedName("ip_list")
    private String[] ipList;

}
