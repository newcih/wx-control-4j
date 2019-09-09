package org.newcih.wxapi.config.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.newcih.wxapi.domain.request.BaseRequestParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;


/**
 * @author NEWCIH
 */
@Configuration
@Aspect
@Order(9)
public class SignAop {

    private final static Logger logger = LoggerFactory.getLogger(SignAop.class);
    private final static Marker SIGN_MARKER = MarkerFactory.getMarker("SIGN");

    /**
     * 所有对外接口
     */
    @Pointcut("execution(* org.newcih.wxapi.web.*.*(..))")
    public void wxapi() {
    }

    /**
     * 微信事件推算
     */
    @Pointcut("execution(* org.newcih.wxapi.web.WechatMessageAccept.messageAccept(..))")
    public void messageAccept() {
    }

    /**
     * 验证签名
     *
     * @param joinPoint
     */
    @Before("wxapi() && !messageAccept()")
    public void signBefore(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            if (arg instanceof BaseRequestParam) {
                BaseRequestParam temp = ((BaseRequestParam) arg);
                String sign = temp.getSign();
                logger.info(SIGN_MARKER, "请求参数签名{}", sign);
            }
        }
    }
}
