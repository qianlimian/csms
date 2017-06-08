package org.smartframework.platform.exception.handler;

import org.smartframework.platform.exception.BusinessException;
import org.smartframework.platform.exception.ValidateException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 业务异常
     */
    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    public String processBusinessException(HttpServletRequest request, HttpServletResponse response,
                                           BusinessException e) {
        response.setStatus(631);
        return e.getMessage();
    }


    /**
     * 校验异常
     */
    @ExceptionHandler(ValidateException.class)
    @ResponseBody
    public String processUnauthenticatedException(HttpServletRequest request, HttpServletResponse response,
                                                  ValidateException e) {
        response.setStatus(632);
        return e.getMessage();
    }

}
