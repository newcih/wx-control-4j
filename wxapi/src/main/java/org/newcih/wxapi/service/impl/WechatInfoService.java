package org.newcih.wxapi.service.impl;

import domain.WechatInfo;
import org.newcih.wxapi.dao.WechatDataInfoMapper;
import org.newcih.wxapi.domain.WechatDataInfo;
import org.newcih.wxapi.service.CacheService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author NEWCIH
 */
@Service
@CacheConfig(cacheNames = "wechat_infos")
@Transactional(rollbackFor = Exception.class)
public class WechatInfoService {

    private static final Logger logger = LoggerFactory.getLogger(WechatInfoService.class);
    private WechatDataInfoMapper wechatDataInfoMapper;
    private CacheService cacheService;

    public WechatInfoService(WechatDataInfoMapper wechatDataInfoMapper, @Qualifier("redisCache") CacheService cacheService) {
        this.wechatDataInfoMapper = wechatDataInfoMapper;
        this.cacheService = cacheService;
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
     * 根据Appid获取AccessToken
     *
     * @param appid
     * @return
     */
    public String getAccessTokenByAppId(String appid) {
        return wechatDataInfoMapper.getAccessTokenByAppid(appid);
    }

    /**
     * 根据Appid获取公众号信息
     *
     * @param appid
     * @return
     */
    public WechatDataInfo getByAppid(String appid) {
        return wechatDataInfoMapper.getByAppid(appid);
    }


}
