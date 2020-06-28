package com.bitwave.admin.service;

import com.bitwave.entity.SysLogs;

import java.util.List;

public interface SysLogsService {

    boolean addLog(SysLogs sysLogs);

    boolean update(SysLogs sysLogs);

    List<SysLogs> selectAllLogs();

    SysLogs selectLogs(Integer id);
}