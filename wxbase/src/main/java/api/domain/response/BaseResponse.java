package api.domain.response;

import com.google.gson.JsonObject;
import lombok.Data;

/**
 * 微信请求基类
 *
 * @author NEWCIH
 */
@Data
public class BaseResponse {

    protected Integer errcode;
    protected String errmsg;

    /**
     * 将JSON请求结果转换为封装好的实际响应类
     *
     * @return 实际请求结果对应封装好的响应类
     */
    public BaseResponse jsonObjectToResponse(JsonObject response) {
        if (response == null) {
            throw new NullPointerException("请求结果JSON对象为NULL");
        }
        this.setErrcode(response.get("errcode").getAsInt());
        this.setErrmsg(response.get("errmsg").getAsString());
        return this;
    }
}
