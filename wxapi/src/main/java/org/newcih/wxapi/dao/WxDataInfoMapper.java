package org.newcih.wxapi.dao;

import org.apache.ibatis.annotations.*;
import org.newcih.wxapi.dao.provider.WxDataInfoProvider;
import org.newcih.wxapi.domain.WxDataInfo;

import java.util.List;

/**
 * @author NEWCIH
 */
@Mapper
public interface WxDataInfoMapper {

    String BASIC_RESULT = "id,appid,appsecret,wechat_id,token_update_time,version";
    String TOKEN_DATA = "access_token,jsapi_ticket,api_ticket,appid";

    /**
     * 查询
     *
     * @return
     */
    @Results(id = "basicMap", value = {
            @Result(property = "wxInfo.appid", column = "appid"),
            @Result(property = "wxInfo.appsecret", column = "appsecret"),
            @Result(property = "wxInfo.wechatId", column = "wechat_id"),
            @Result(property = "tokenUpdateTime", column = "token_update_time"),
            @Result(property = "wxInfo.accessToken", column = "access_token"),
            @Result(property = "wxInfo.apiTicket", column = "api_ticket"),
            @Result(property = "wxInfo.jsapiTicket", column = "jsapi_ticket")
    })
    @Select({"SELECT", BASIC_RESULT, "FROM wx_info WHERE status = '1'"})
    List<WxDataInfo> list();

    /**
     * 更新
     *
     * @param wxDataInfo
     * @return
     */
    @UpdateProvider(type = WxDataInfoProvider.class, method = "updateToken")
    Integer updateToken(WxDataInfo wxDataInfo);

    @Select({"SELECT", BASIC_RESULT, "FROM wx_info WHERE status = '1' AND id = #{id}"})
    WxDataInfo getById(@Param("id") Integer id);

    @ResultMap("basicMap")
    @Select("SELECT appid,appsecret FROM wx_info WHERE wechat_id = #{wechatId} AND status = '1'")
    WxDataInfo getByWechatId(@Param("wechatId") String wechatId);

    @Results({
            @Result(property = "accessToken", column = "access_token")
    })
    @Select("SELECT access_token FROM wx_info WHERE appid = #{appid} and status = '1'")
    String getAccessTokenByAppid(@Param("appid") String appid);

    @ResultMap("basicMap")
    @Select({"SELECT", TOKEN_DATA, "FROM wx_info WHERE appid = #{appid} AND status = '1'"})
    WxDataInfo getByAppid(@Param("appid") String appid);
}
