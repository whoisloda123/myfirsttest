package com.liucan.boot.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author liucan
 * @date 2018/6/3
 * @brief 业务异常
 * 1.ResponseStatus放到类上，在抛出异常给用户时设置状态
 * 2.ResponseStatus放在方法上，无论它执行方法过程中有没有异常产生，
 * 用户都会得到异常的界面。而目标方法正常执行，一般和@ExceptionHandler一起使用
 */
@ResponseStatus(value = HttpStatus.BAD_GATEWAY, reason = "业务异常")
public class BizException extends RuntimeException {
    public BizException(String message) {
        super(message);
    }
}
