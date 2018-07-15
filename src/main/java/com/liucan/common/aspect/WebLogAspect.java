package com.liucan.common.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

/**
 * @author liucan
 * @date 2018/7/10
 * @brief 此处记录请求日志
 */
@Slf4j
@Aspect
@Component
@EnableAspectJAutoProxy
public class WebLogAspect {
    @Pointcut("execution(* com.liucan.controller.MyRestController.*(..))")
    private void pointcutId() {
    }

    @Before("pointcutId()")
    public void beforeTask(JoinPoint jp) {
        log.info("[记录MyRestController请求]函数：{}，准备执行", jp.getSignature().toString());
    }

    @After("pointcutId()")
    public void afterTask(JoinPoint jp) {
        log.info("[记录MyRestController请求]函数：{}，开始执行", jp.getSignature().toString());
    }

    @AfterReturning(pointcut = "pointcutId()", returning = "retVal")
    public void afterRetruningTask(JoinPoint jp, Object retVal) {
        log.info("[记录MyRestController请求]函数：{}，执行完成，返回结果：{}", jp.getSignature().toString(), retVal);
    }

    @AfterThrowing(pointcut = "pointcutId()", throwing = "ex")
    public void afterThrowingTask(JoinPoint jp, Exception ex) {
        log.info("[记录MyRestController请求]函数：{}，执行抛出异常", jp.getSignature().toString(), ex);
    }

    @Around("execution(* com.liucan.controller.MyController.*(..))")
    public Object aroundTask(ProceedingJoinPoint jp) throws Throwable {
        Object obj;
        try {
            log.info("[记录MyController请求]函数：{}，准备执行，可对参数进行修改", jp.getSignature().toString());
            obj = jp.proceed();
            log.info("[记录MyController请求]函数：{}，执行完成，返回结果：{}", jp.getSignature().toString(), obj);
        } catch (Exception e) {
            log.info("[记录MyController请求]函数：{}，执行抛出异常", jp.getSignature().toString(), e);
            throw new RuntimeException(e);
        }

        return obj;
    }
}
