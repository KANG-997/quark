package com.bitwave.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bitwave.admin.service.SysLogsService;
import com.bitwave.entity.SysLogs;
import com.bitwave.mapper.SysLogsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SysLogsServiceImpl implements SysLogsService {

    @Resource
    private SysLogsMapper sysLogsMapper;

    @Override
    public boolean addLog(SysLogs sysLogs) {
        int insert = sysLogsMapper.insert(sysLogs);
        if (insert < 1){
            return false;
        }
        return true;
    }

    @Override
    public boolean update(SysLogs sysLogs) {
        int id = sysLogsMapper.update(sysLogs, new QueryWrapper<SysLogs>().eq("id", sysLogs.getId()));
        if (id < 1){
            return false;
        }
        return true;
    }

    @Override
    public List<SysLogs> selectAllLogs() {
        return null;
    }

    @Override
    public SysLogs selectLogs(Integer id) {
        return null;
    }
}
