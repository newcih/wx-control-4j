package org.newcih.wxapi.web;

import org.newcih.wxapi.domain.response.DefaultResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author NEWCIH
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public DefaultResponse exception(Exception e) {
        log.error("全局通用异常捕获", e);
        return DefaultResponse.fail(e.getMessage());
    }

}
