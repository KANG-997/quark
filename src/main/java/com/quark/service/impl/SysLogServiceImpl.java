package com.quark.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.quark.annon.LogInfo;
import com.quark.entity.SysLog;
import com.quark.mapper.SysLogMapper;
import com.quark.service.SysLogService;
import com.quark.utils.IP2RegionUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.*;

@Service
public class SysLogServiceImpl implements SysLogService {
    @Resource
    private SysLogMapper sysLogMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void saveLog(ProceedingJoinPoint joinPoint, SysLog sysLog) throws JsonProcessingException {
        MethodSignature methodSignature = (MethodSignature)joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        LogInfo logInfo = method.getAnnotation(LogInfo.class);
        if (logInfo != null) {
            sysLog.setAction(logInfo.value());
        }
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = methodSignature.getName();
        sysLog.setMethod(className + "." + methodName + "()");
        Object[] args = joinPoint.getArgs();
        LocalVariableTableParameterNameDiscoverer methodParamsName = new LocalVariableTableParameterNameDiscoverer();
        String[] parameterNames = methodParamsName.getParameterNames(method);
        if (args != null && parameterNames != null) {
            StringBuilder params = new StringBuilder();
            params = handleParams(params, args, Arrays.asList(parameterNames));
            sysLog.setParams(params.toString());
        }
        sysLog.setCreateTime(new Date());
        sysLog.setLocation(IP2RegionUtil.getCityInfo(sysLog.getIp()));
        sysLogMapper.insert(sysLog);
    }

    private StringBuilder handleParams(StringBuilder params, Object[] args, List paramNames) throws JsonProcessingException {
        for (int i = 0; i < args.length; i++) {
            if (args[i] instanceof Map){
                Set set = ((Map) args[i]).keySet();
                List<Object> list = new ArrayList<>();
                List<Object> paramList = new ArrayList<>();
                for (Object key : set) {
                    list.add(((Map)args[i]).get(key));
                    paramList.add(key);
                }
                return handleParams(params,list.toArray(),paramList);
            }else {
                if (args[i] instanceof Serializable) {
                    Class<?> aClass = args[i].getClass();
                    try {
                        aClass.getDeclaredMethod("toString",new Class[]{null});
                        params.append(" ").append(paramNames.get(i)).append(": ").append(objectMapper.writeValueAsString(args[i]));
                    } catch (NoSuchMethodException e) {
                        params.append(" ").append(paramNames.get(i)).append(": ").append(objectMapper.writeValueAsString(args[i].toString()));
                    }
                }else if (args[i] instanceof MultipartFile){
                    MultipartFile file = (MultipartFile) args[i];
                    params.append(" ").append(paramNames.get(i)).append(": ").append(file.getName());
                }else{
                    params.append(" ").append(paramNames.get(i)).append(args[i]);
                }
            }
        }
        return params;
    }
}
