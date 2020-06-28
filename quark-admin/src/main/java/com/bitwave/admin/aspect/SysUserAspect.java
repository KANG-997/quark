package com.bitwave.admin.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class SysUserAspect {

    @Pointcut(value = "@annotation(com.bitwave.admin.anno.verifyParams)")
    public void pointcut(){}

    @Before("pointcut()")
    public void before(JoinPoint joinPoint){
        String method = joinPoint.getSignature().getName();
        if (method.contains("login")) {
            for (Object arg : joinPoint.getArgs()) {
                if (arg == null){
                    throw  new NullPointerException("参数不合法！");
                }
            }
        }
    }
}
