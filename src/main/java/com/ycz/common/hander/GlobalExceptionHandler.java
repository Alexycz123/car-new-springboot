package com.ycz.common.hander;

import com.ycz.common.CodeMsg;
import com.ycz.common.Result;
import com.ycz.common.exception.BussiException;
import org.apache.shiro.ShiroException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Description: 全局异常处理器
 * @author: Todd Ding
 * @date 2020-11-30 12:00
 */
//全局异常处理器
@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler
    public Object handlerException(Exception exception) {
        exception.printStackTrace();//打印异常

        // 判断是否是程序员自己定义的异常信息
        if (exception instanceof BussiException) {
            BussiException bussiException = (BussiException) exception;
            return new Result(bussiException);
        }
        // shiro
        if (exception instanceof ShiroException) {
            return null;
        }
        // 可能发生是其他异常
        return new Result(CodeMsg.ERROR);
    }
}