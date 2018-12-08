package com.liucan.common.aspect;

import com.liucan.common.annotation.LoginCheck;
import com.liucan.common.annotation.UserId;
import com.liucan.common.response.CommonResponse;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * @author liucan
 * @version 18-12-8
 */
@Component
@Aspect
public class LoginAspect {
    private final HttpServletRequest request;

    public LoginAspect(HttpServletRequest request) {
        this.request = request;
    }

    private Integer userId() {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equalsIgnoreCase("uid")) {
                    return Integer.valueOf(cookie.getValue());
                }
            }
        }
        return null;
    }

    @Around("@annotation(com.liucan.common.annotation.LoginCheck)")
    private Object aroundLogin(ProceedingJoinPoint jp) throws Throwable {
        try {
            Integer userId = userId();
            Object[] args = jp.getArgs();
            Method method = ((MethodSignature) jp.getSignature()).getMethod();

            if (method.getAnnotation(LoginCheck.class).required() && userId == null) {
                return CommonResponse.error(403, "未登录");
            }

            Parameter[] parameters = method.getParameters();
            for (int i = 0; i < parameters.length; i++) {
                if (parameters[i].isAnnotationPresent(UserId.class)) {
                    args[i] = userId;
                }
            }
            return jp.proceed(args);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
