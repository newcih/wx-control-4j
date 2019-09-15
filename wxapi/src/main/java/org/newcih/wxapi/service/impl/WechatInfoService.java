package org.newcih.wxapi.service.impl;

import domain.WechatInfo;
import org.newcih.wxapi.dao.WechatDataInfoMapper;
import org.newcih.wxapi.domain.WechatDataInfo;
import org.newcih.wxapi.service.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author NEWCIH
 */
@Service
@CacheConfig(cacheNames = "wechat_infos")
@Transactional(rollbackFor = Exception.class)
public class WechatInfoService extends BaseService {

    private static final Logger logger = LoggerFactory.getLogger(WechatInfoService.class);
    private WechatDataInfoMapper wechatDataInfoMapper;
    private RedisTemplate<String, String> redisTemplate;

    public WechatInfoService(WechatDataInfoMapper wechatDataInfoMapper, RedisTemplate redisTemplate) {
        this.wechatDataInfoMapper = wechatDataInfoMapper;
        this.redisTemplate = redisTemplate;
    }


    /**
     * 根据微信号获取公众号信息
     *
     * @param wechatId
     * @return
     */
    @Cacheable(key = "#wechatId")
    public WechatDataInfo getWechatInfoByWechatId(String wechatId) {
        logger.info("{}读取数据库", wechatId);
        return wechatDataInfoMapper.getByWechatId(wechatId);
    }

    /**
     * 获取公众号列表
     *
     * @return
     */
    public List<WechatDataInfo> listWechatDataInfos() {
        return wechatDataInfoMapper.list();
    }

    /**
     * 根据数据库主键获取公众号信息
     *
     * @param id
     * @return
     */
    public WechatDataInfo getWechatDataInfo(Integer id) {
        return wechatDataInfoMapper.getById(id);
    }

    /**
     * 检测access token是否过期，如果过期，则刷新
     *
     * @param wechatInfo
     * @deprecated 暂时不启用
     */
    @Deprecated
    public void handleExpireAccessToken(WechatInfo wechatInfo) {
    }

    /**
     * 暂不支持校验js api ticket的有效性
     *
     * @param wechatInfo
     * @deprecated 暂时不启用
     */
    @Deprecated
    public void handleExpireJsApiTicket(WechatInfo wechatInfo) {
    }

    /**
     * 暂不支持校验api ticket的有效性
     *
     * @param wechatInfo
     * @deprecated 暂时不启用
     */
    @Deprecated
    public void handleExpireApiTicket(WechatInfo wechatInfo) {
    }

    @CachePut(key = "#wechatDataInfo.wechatInfo.wechatId")
    public boolean updateWechatInfo(WechatDataInfo wechatDataInfo) {
        return true;
    }

    @CachePut(key = "#wechatDataInfo.wechatInfo.wechatId")
    public boolean insertWechatInfo(WechatDataInfo wechatDataInfo) {
        return true;
    }

    @CacheEvict(key = "#wechatDataInfo.wechatInfo.wechatId")
    public boolean deleteWechatInfo(WechatDataInfo wechatDataInfo) {
        return true;
    }

    /**
     * 根据appid获取access token
     *
     * @param appid
     * @return
     */
    public String getAccessTokenByAppId(String appid) {
        return redisTemplate.opsForValue().get(TokenService.class.getName() + TokenService.REDIS_KEY_ACCESS_TOKEN_SUFFIX);
    }

    /**
     * 根据appid获取js api ticket
     *
     * @param appid
     * @return
     */
    public String getJsApiTicketByAppId(String appid) {
        return redisTemplate.opsForValue().get(TokenService.class.getName() + TokenService.REDIS_KEY_JS_API_TICKET_SUFFIX);
    }

    /**
     * 根据appid获取api ticket
     *
     * @param appid
     * @return
     */
    public String getApiTicketByAppId(String appid) {
        return redisTemplate.opsForValue().get(TokenService.class.getName() + TokenService.REDIS_KEY_API_TICKET_SUFFIX);
    }

    /**
     * 根据Appid获取公众号信息
     *
     * @param appid
     * @return
     */
    @Cacheable(key = "#appid")
    public WechatDataInfo getByAppid(String appid) {
        return wechatDataInfoMapper.getByAppId(appid);
    }


}
