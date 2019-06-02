package org.newcih.wxapi.config.aop;

import domain.WxInfo;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.newcih.wxapi.domain.WxDataInfo;
import org.newcih.wxapi.domain.response.Response;
import org.newcih.wxapi.service.impl.CommonServiceImpl;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

/**
 * 为开放接口添加WxInfo入参
 *
 * @author NEWCIH
 */
@Aspect
@Configuration
public class FixWxInfoAop {

    private final CommonServiceImpl commonService;

    public FixWxInfoAop(CommonServiceImpl commonService) {
        this.commonService = commonService;
    }

    /**
     * 所有对外接口
     */
    @Pointcut("execution(* org.newcih.wxapi.web.*.*(..))")
    public void wxapi() {
    }

    @Around("wxapi()")
    public Object fixWxInfoParams(ProceedingJoinPoint joinPoint) {
        try {
            Object[] args = joinPoint.getArgs();
            for (int i = 0; i < args.length; i++) {
                if (args[i] instanceof WxInfo) {
                    WxInfo requestWxInfo = (WxInfo) args[i];
                    if (StringUtils.isEmpty(requestWxInfo.getAppid())) {
                        return Response.fail("接口需要公众号信息，APPID不能为空");
                    }
                    String appid = requestWxInfo.getAppid();
                    WxDataInfo wxDataInfo = commonService.getByAppid(appid);
                    if (wxDataInfo == null) {
                        return Response.fail("不存在APPID为" + appid + "的公众号");
                    }
                    // 一定要执行替换动作，避免受HTTP的GET参数污染对象
                    args[i] = wxDataInfo;
                }
            }
            return joinPoint.proceed(args);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            return Response.fail("服务器异常");
        }
    }
}
