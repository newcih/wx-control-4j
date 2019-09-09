package api.service;

import api.domain.eunm.ApiUrlEnum;
import api.domain.params.common.GetCallBackIPParam;
import api.domain.params.common.TokenParam;
import api.domain.params.jssdk.GetTicketParam;
import api.domain.response.common.GetCallBackIPResponse;
import api.domain.response.common.TokenResponse;
import api.domain.response.jssdk.GetTicketResponse;
import domain.WechatInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.function.BiFunction;

/**
 * @author newcih
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class WechatRequest extends BaseWechatRequest {

    public static BiFunction<TokenParam, WechatInfo, TokenResponse> getToken = (tokenParam, wechatInfo) -> request(tokenParam, ApiUrlEnum.TOKEN, wechatInfo, TokenResponse.class);

    public static BiFunction<GetTicketParam, WechatInfo, GetTicketResponse> getTicket = (getTicketParam, wechatInfo) -> request(getTicketParam, ApiUrlEnum.TICKET_GET_TICKET, wechatInfo, GetTicketResponse.class);

    public static BiFunction<GetCallBackIPParam, WechatInfo, GetCallBackIPResponse> getCallBackIP = (getCallBackIPParam, wechatInfo) -> request(getCallBackIPParam, ApiUrlEnum.GET_CALLBACK_IP, wechatInfo, GetCallBackIPResponse.class);

}
