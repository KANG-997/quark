package com.quark.aspect;

import com.quark.authentication.JwtUtil;
import com.quark.entity.SysLog;
import com.quark.service.SysLogService;
import com.quark.utils.HttpContextUtil;
import com.quark.utils.IPUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Aspect
@Component
public class LogAspect {

    @Autowired
    private SysLogService sysLogService;

    @Pointcut("@annotation(com.quark.annon.LogInfo)")
    public void pointcut(){

    }

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable{
        long start = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        HttpServletRequest httpServletRequest = HttpContextUtil.getHttpServletRequest();
        String ipAddr = IPUtils.getIpAddr(httpServletRequest);
        long time_cost = System.currentTimeMillis() - start;
        String token = JwtUtil.subToken(httpServletRequest.getHeader("Authorization"));
        log.info("token:{}",token);
        String username = "";
        if (StringUtils.isNotBlank(token)) {
            username = JwtUtil.getUsername(token);
        }
        SysLog sysLog = new SysLog();
        sysLog.setUsername(username);
        sysLog.setIp(ipAddr);
        sysLog.setTimeCost(time_cost+"/ms");
        sysLogService.saveLog(joinPoint,sysLog);
        return  result;
    }
}
