package com.bitwave.admin.aspect;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.bitwave.admin.anno.LogInfo;
import com.bitwave.admin.service.SysLogsService;
import com.bitwave.entity.SysLogs;
import com.bitwave.enums.LogCode;
import com.bitwave.response.ResultResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.jni.Proc;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Aspect
@Component
@Slf4j
public class LogAspect {

    private Integer logId;

    @Resource
    private SysLogsService sysLogsService;

    @Pointcut("@annotation(com.bitwave.admin.anno.LogInfo)")
    private void pointCut(){
    }


    @Before("pointCut()")
    public void Before(JoinPoint joinPoint){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String requestMethod = request.getMethod();
        String requestURI = request.getRequestURI();
        String contentType = request.getContentType();
        String remoteHost = request.getRemoteHost();
        log.info("---------------START---------------");
        log.info("请求类型:{}",requestMethod);
        log.info("请求路径:{}",requestURI);
        log.info("内容类型:{}",contentType);
        log.info("远程地址:{}",remoteHost);

    }

    @Around("pointCut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long endTime = System.currentTimeMillis();
        long spendTime = endTime - startTime;
        ResultResponse res = (ResultResponse) result;
        Object data = res.getData();
        saveLog(joinPoint,request,spendTime);
        log.info("result:{}",data);
        return result;

    }

    @After("pointCut()")
    public void after(JoinPoint joinPoint){
        String now = DateUtil.now();
        log.info("请求处理日期:{}",now);
        log.info("----------------end----------------");
    }

    private void saveLog(ProceedingJoinPoint joinPoint,HttpServletRequest request,Long seconds){
        SysLogs sysLogs = new SysLogs();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        String requestMethod = request.getMethod();
        String requestURI = request.getRequestURI();
        String contentType = request.getContentType();
        String remoteHost = request.getRemoteHost();
        LogInfo logInfo = method.getAnnotation(LogInfo.class);
        if (logInfo != null) {
            sysLogs.setRequestmethod(requestMethod);
            sysLogs.setRequesturi(requestURI);
            sysLogs.setRequestcontenttype(contentType);
            sysLogs.setRemotehost(remoteHost);
            LogCode logCode = logInfo.operatorType();
            String value = logInfo.value();
            sysLogs.setOperatortype("["+logCode.getCode()+","+value+"]");
            DateTime date = DateUtil.date();
            sysLogs.setRequestdate(date);
            sysLogsService.addLog(sysLogs);
            log.info("操作类型:{},操作内容：{}",logCode.getCode(),value);
            Object[] args = joinPoint.getArgs();
            List<Object> objects = Arrays.asList(args);
            log.info("请求参数:{}",objects);
            log.info("处理时间:{}/ms",seconds);
        }
    }

}
