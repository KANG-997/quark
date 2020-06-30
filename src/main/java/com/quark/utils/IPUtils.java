package com.quark.utils;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

public class IPUtils {

    private static final String UNKNOW = "unknown";

    protected IPUtils(){}

    public static String getIpAddr(HttpServletRequest request){
        String ip = request.getHeader("x-forwarded-for");
        if (StringUtils.isBlank(ip) || UNKNOW.equalsIgnoreCase(ip)){
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (StringUtils.isBlank(ip) || UNKNOW.equalsIgnoreCase(ip)){
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StringUtils.isBlank(ip) || UNKNOW.equalsIgnoreCase(ip)){
            ip = request.getRemoteAddr();
        }
        return "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : ip;
    }
}
