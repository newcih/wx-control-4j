package org.newcih.wxapi.dao;

import org.apache.ibatis.annotations.*;
import org.newcih.wxapi.dao.provider.WechatDataInfoProvider;
import org.newcih.wxapi.domain.WechatDataInfo;

import java.util.List;

/**
 * @author NEWCIH
 */
@Mapper
public interface WechatDataInfoMapper {

    String BASIC_RESULT = "id,appid,appsecret,wechat_id,token_update_time,version,aes_token,aes_key";
    String TOKEN_DATA = "access_token,jsapi_ticket,api_ticket,appid";

    /**
     * 查询
     *
     * @return
     */
    @Results(id = "basicMap", value = {
            @Result(property = "wechatInfo.appid", column = "appid"),
            @Result(property = "wechatInfo.appsecret", column = "appsecret"),
            @Result(property = "wechatInfo.wechatId", column = "wechat_id"),
            @Result(property = "tokenUpdateTime", column = "token_update_time"),
            @Result(property = "wechatInfo.accessToken", column = "access_token"),
            @Result(property = "wechatInfo.apiTicket", column = "api_ticket"),
            @Result(property = "wechatInfo.jsapiTicket", column = "jsapi_ticket"),
            @Result(property = "wechatInfo.token", column = "aes_token"),
            @Result(property = "wechatInfo.encodingAesKey", column = "aes_key")
    })
    @Select({"SELECT ", BASIC_RESULT, " FROM wechat_info WHERE is_enabled = 1"})
    List<WechatDataInfo> list();

    /**
     * 更新
     *
     * @param wxDataInfo
     * @return
     */
    @UpdateProvider(type = WechatDataInfoProvider.class, method = "updateToken")
    Integer updateToken(WechatDataInfo wxDataInfo);

    @Select({"SELECT", BASIC_RESULT, "FROM wechat_info WHERE is_enabled = 1 AND id = #{id}"})
    WechatDataInfo getById(@Param("id") Integer id);

    @ResultMap("basicMap")
    @Select({"SELECT ", BASIC_RESULT, " FROM wechat_info WHERE wechat_id = #{wechatId} AND is_enabled = 1"})
    WechatDataInfo getByWechatId(@Param("wechatId") String wechatId);

    @Results({
            @Result(property = "accessToken", column = "access_token")
    })
    @Select("SELECT access_token FROM wechat_info WHERE appid = #{appid} and is_enabled = 1")
    String getAccessTokenByAppid(@Param("appid") String appid);

    @ResultMap("basicMap")
    @Select({"SELECT", TOKEN_DATA, "FROM wechat_info WHERE appid = #{appid} AND is_enabled = 1"})
    WechatDataInfo getByAppid(@Param("appid") String appid);
}
