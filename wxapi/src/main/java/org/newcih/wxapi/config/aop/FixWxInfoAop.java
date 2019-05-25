package org.newcih.wxapi.config.aop;

import api.domain.params.BaseParam;
import domain.WxInfo;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.newcih.wxapi.config.prop.WxProperties;
import org.newcih.wxapi.domain.response.DefaultResponse;
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

    private final WxProperties wxProperties;

    public FixWxInfoAop(WxProperties wxProperties) {
        this.wxProperties = wxProperties;
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
            BaseParam params = null;
            for (int i = 0; i < args.length; i++) {
                if (args[i] instanceof BaseParam) {
                    params = (BaseParam) args[i];
                }
                if (args[i] instanceof WxInfo) {
                    if (StringUtils.isEmpty(params.getAppid())) {
                        return DefaultResponse.fail("接口需要公众号信息，APPID不能为空");
                    }
                    String appid = params.getAppid();
                    WxInfo wxInfo = wxProperties.getAppIdInnerMap().get(appid);
                    if (wxInfo == null) {
                        return DefaultResponse.fail("不存在APPID为" + appid + "的公众号");
                    }
                    // 一定要执行替换动作，避免受HTTP的GET参数污染对象
                    args[i] = wxInfo;
                }
            }
            return joinPoint.proceed(args);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            return DefaultResponse.fail("服务器异常");
        }
    }
}
