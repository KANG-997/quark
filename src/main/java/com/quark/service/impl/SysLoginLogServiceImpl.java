package com.quark.service.impl;

import com.quark.entity.SysLoginLog;
import com.quark.mapper.SysLoginLogMapper;
import com.quark.service.SysLoginLogService;
import com.quark.utils.HttpContextUtil;
import com.quark.utils.IP2RegionUtil;
import com.quark.utils.IPUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Service
@Slf4j
public class SysLoginLogServiceImpl implements SysLoginLogService {

    @Resource
    private SysLoginLogMapper sysLoginLogMapper;

    @Override
    public void saveLoginLog(SysLoginLog sysLoginLog) {
        sysLoginLog.setLoginTme(new Date());
        HttpServletRequest httpServletRequest = HttpContextUtil.getHttpServletRequest();
        String ipAddr = IPUtils.getIpAddr(httpServletRequest);
        String cityInfo = IP2RegionUtil.getCityInfo(ipAddr);
        sysLoginLog.setIp(ipAddr);
        sysLoginLog.setLocation(cityInfo);
        sysLoginLogMapper.insert(sysLoginLog);
    }
}
