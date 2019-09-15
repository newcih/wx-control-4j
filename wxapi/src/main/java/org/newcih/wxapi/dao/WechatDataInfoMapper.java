package org.newcih.wxapi.dao;

import org.apache.ibatis.annotations.*;
import org.newcih.wxapi.domain.WechatDataInfo;

import java.util.List;

/**
 * @author NEWCIH
 */
@Mapper
public interface WechatDataInfoMapper {

    String BASIC_RESULT = "id,appid,appsecret,wechat_id,version,aes_token,aes_key";

    /**
     * 查询
     *
     * @return
     */
    @Results(id = "basicMap", value = {
            @Result(property = "wechatInfo.appid", column = "appid"),
            @Result(property = "wechatInfo.appsecret", column = "appsecret"),
            @Result(property = "wechatInfo.wechatId", column = "wechat_id"),
            @Result(property = "wechatInfo.token", column = "aes_token"),
            @Result(property = "wechatInfo.encodingAesKey", column = "aes_key")
    })
    @Select({"SELECT", BASIC_RESULT, "FROM wechat_info WHERE is_enabled = 1"})
    List<WechatDataInfo> list();

    @Select({"SELECT", BASIC_RESULT, "FROM wechat_info WHERE is_enabled = 1 AND id = #{id}"})
    WechatDataInfo getById(@Param("id") Integer id);

    @Select({"SELECT", BASIC_RESULT, "FROM wechat_info WHERE is_enabled = 1 AND appid = #{appid}"})
    WechatDataInfo getByAppId(@Param("appid") String appid);

    @ResultMap("basicMap")
    @Select({"SELECT", BASIC_RESULT, "FROM wechat_info WHERE wechat_id = #{wechatId} AND is_enabled = 1"})
    WechatDataInfo getByWechatId(@Param("wechatId") String wechatId);

}
