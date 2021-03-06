package org.newcih.wxapi.web;

import org.newcih.wxapi.domain.response.Response;
import org.newcih.wxapi.exception.SignWrongException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author NEWCIH
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public Response exception(Exception e) {
        log.error("全局通用异常捕获", e);
        return Response.fail(e.getMessage());
    }

    @ExceptionHandler(SignWrongException.class)
    public Response signWrongException(SignWrongException s) {
        if (log.isDebugEnabled()) {
            log.debug("接口请求签名错误", s);
        }
        return Response.fail(s.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Response handleArgumentNotValid(MethodArgumentNotValidException anv) {
        return Response.fail(anv.getBindingResult().getFieldError().getDefaultMessage());
    }
}
