package org.newcih.wxapi.config.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.newcih.wxapi.domain.WechatDataInfo;
import org.newcih.wxapi.domain.request.BaseRequestParam;
import org.newcih.wxapi.domain.response.Response;
import org.newcih.wxapi.service.impl.WechatInfoService;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.util.StringUtils;

/**
 * 为开放接口添加WechatInfo入参
 *
 * @author NEWCIH
 */
@Aspect
@Configuration
@Order(10)
public class FixWechatInfoAop {

    private WechatInfoService wechatInfoService;

    public FixWechatInfoAop(WechatInfoService wechatInfoService) {
        this.wechatInfoService = wechatInfoService;
    }

    /**
     * 所有对外接口
     */
    @Pointcut("execution(* org.newcih.wxapi.web.*.*(..))")
    public void wxapi() {
    }

    @Around("wxapi()")
    public Object fixWechatInfoParams(ProceedingJoinPoint joinPoint) {
        try {
            Object[] args = joinPoint.getArgs();
            WechatDataInfo temp = null;
            int wxDataInfoIndex = -1;

            for (int i = 0; i < args.length; i++) {
                if (args[i] instanceof BaseRequestParam) {
                    BaseRequestParam baseRequestParam = (BaseRequestParam) args[i];
                    if (StringUtils.isEmpty(baseRequestParam.getAppid())) {
                        return Response.fail("接口需要公众号信息，APPID不能为空");
                    }
                    String appid = baseRequestParam.getAppid();
                    WechatDataInfo wxDataInfo = wechatInfoService.getByAppid(appid);
                    if (wxDataInfo == null) {
                        return Response.fail("不存在APPID为" + appid + "的公众号");
                    }
                    temp = wxDataInfo;
                } else if (args[i] instanceof WechatDataInfo) {
                    wxDataInfoIndex = i;
                }
            }

            // 一定要执行替换动作，避免受HTTP的GET参数污染对象
            if (wxDataInfoIndex > -1) {
                args[wxDataInfoIndex] = temp;
            }

            return joinPoint.proceed(args);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            return Response.fail("服务器异常");
        }
    }
}
