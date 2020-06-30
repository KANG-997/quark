package com.quark.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.quark.entity.SysLog;
import org.aspectj.lang.ProceedingJoinPoint;

public interface SysLogService {

    void saveLog(ProceedingJoinPoint joinPoint, SysLog sysLog) throws JsonProcessingException;
}
