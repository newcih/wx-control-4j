package api.domain.response.account;

import api.domain.response.BaseResponse;
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

}
