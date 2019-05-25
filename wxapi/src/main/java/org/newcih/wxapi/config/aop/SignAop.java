package org.newcih.wxapi.config.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;


/**
 * @author NEWCIH
 */
@Configuration
@Aspect
public class SignAop {

    /**
     * 所有对外接口
     */
    @Pointcut("execution(* org.newcih.wxapi.web.*.*(..))")
    public void wxapi() {
    }

    /**
     * 微信事件推算
     */
    @Pointcut("execution(* org.newcih.wxapi.web.WxMessageAccept.messageAccept(..))")
    public void messageAccept() {
    }

    /**
     * 验证签名
     *
     * @param joinPoint
     */
    @Before("wxapi() && !messageAccept()")
    public void signBefore(JoinPoint joinPoint) {
        System.out.println("[AOP] 开始验证签名");
    }
}
