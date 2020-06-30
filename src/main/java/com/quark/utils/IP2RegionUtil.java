package com.quark.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.lionsoul.ip2region.*;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;

@Slf4j
public class IP2RegionUtil {

    public static String getCityInfo(String ip){
        DbSearcher searcher = null;
        try {
            String dbPath = IP2RegionUtil.class.getResource("/ip2region/ip2region.db").getPath();
            File file = new File(dbPath);
            if (!file.exists()) {
                String tempDir = System.getProperties().getProperty("java.io.tmpdir");
                dbPath = tempDir + "ip.db";
                FileUtils.copyInputStreamToFile(Objects.requireNonNull(IP2RegionUtil.class.getClassLoader().getResourceAsStream("classpath:ip2region/ip2region.db")), file);
                }
            int algorithm = DbSearcher.BTREE_ALGORITHM;
            DbConfig dbConfig = new DbConfig();
            searcher = new DbSearcher(dbConfig, file.getPath());
            Method method = null;
            method = searcher.getClass().getMethod("btreeSearch", String.class);
            DataBlock dataBlock = null;
            if (!Util.isIpAddress(ip)) {
                log.error("invalid ip address");
            }
            dataBlock = (DataBlock) method.invoke(searcher,ip);
            return dataBlock.getRegion();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
