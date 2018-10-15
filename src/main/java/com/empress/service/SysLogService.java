package com.empress.service;

import com.empress.beans.LogType;
import com.empress.common.RequestHolder;
import com.empress.dao.SysLogMapper;
import com.empress.pojo.*;
import com.empress.util.IpUtil;
import com.empress.util.JsonMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author Hystar
 * @date 2018/10/12 0012
 */
@Service
public class SysLogService {

    @Resource
    private SysLogMapper sysLogMapper;

    /**
     * 添加部门操作日志
     *
     * @param before
     * @param after
     */
    public void saveDeptLogLog(SysDept before, SysDept after) {
        SysLogWithBLOBs sysLogWithBLOBs = new SysLogWithBLOBs();
        sysLogWithBLOBs.setType(LogType.TYPE_DEPT);
        sysLogWithBLOBs.setTargetId(after == null ? before.getId() : after.getId());
        sysLogWithBLOBs.setOldValue(before == null ? "" : JsonMapper.objToString(before));
        sysLogWithBLOBs.setNewValue(after == null ? "" : JsonMapper.objToString(after));
        sysLogWithBLOBs.setOperator(RequestHolder.getCurrentUser().getUsername());
        sysLogWithBLOBs.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        sysLogWithBLOBs.setOperateTime(new Date());
        sysLogWithBLOBs.setStatus(0);
        sysLogMapper.insertSelective(sysLogWithBLOBs);
    }

    public void saveUserLog(SysUser before, SysUser after) {
        SysLogWithBLOBs sysLogWithBLOBs = new SysLogWithBLOBs();
        sysLogWithBLOBs.setType(LogType.TYPE_USER);
        sysLogWithBLOBs.setTargetId(after == null ? before.getId() : after.getId());
        sysLogWithBLOBs.setOldValue(before == null ? "" : JsonMapper.objToString(before));
        sysLogWithBLOBs.setNewValue(after == null ? "" : JsonMapper.objToString(after));
        sysLogWithBLOBs.setOperator(RequestHolder.getCurrentUser().getUsername());
        sysLogWithBLOBs.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        sysLogWithBLOBs.setOperateTime(new Date());
        sysLogWithBLOBs.setStatus(0);
        sysLogMapper.insertSelective(sysLogWithBLOBs);
    }

    public void saveAclModuleLog(SysAclModule before, SysAclModule after) {
        SysLogWithBLOBs sysLogWithBLOBs = new SysLogWithBLOBs();
        sysLogWithBLOBs.setType(LogType.TYPE_ACL_MODULE);
        sysLogWithBLOBs.setTargetId(after == null ? before.getId() : after.getId());
        sysLogWithBLOBs.setOldValue(before == null ? "" : JsonMapper.objToString(before));
        sysLogWithBLOBs.setNewValue(after == null ? "" : JsonMapper.objToString(after));
        sysLogWithBLOBs.setOperator(RequestHolder.getCurrentUser().getUsername());
        sysLogWithBLOBs.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        sysLogWithBLOBs.setOperateTime(new Date());
        sysLogWithBLOBs.setStatus(0);
        sysLogMapper.insertSelective(sysLogWithBLOBs);
    }

    public void saveAclLog(SysAcl before, SysAcl after) {
        SysLogWithBLOBs sysLogWithBLOBs = new SysLogWithBLOBs();
        sysLogWithBLOBs.setType(LogType.TYPE_ACL);
        sysLogWithBLOBs.setTargetId(after == null ? before.getId() : after.getId());
        sysLogWithBLOBs.setOldValue(before == null ? "" : JsonMapper.objToString(before));
        sysLogWithBLOBs.setNewValue(after == null ? "" : JsonMapper.objToString(after));
        sysLogWithBLOBs.setOperator(RequestHolder.getCurrentUser().getUsername());
        sysLogWithBLOBs.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        sysLogWithBLOBs.setOperateTime(new Date());
        sysLogWithBLOBs.setStatus(0);
        sysLogMapper.insertSelective(sysLogWithBLOBs);
    }

    public void saveRoleLog(SysRole before, SysRole after) {
        SysLogWithBLOBs sysLogWithBLOBs = new SysLogWithBLOBs();
        sysLogWithBLOBs.setType(LogType.TYPE_ROLE);
        sysLogWithBLOBs.setTargetId(after == null ? before.getId() : after.getId());
        sysLogWithBLOBs.setOldValue(before == null ? "" : JsonMapper.objToString(before));
        sysLogWithBLOBs.setNewValue(after == null ? "" : JsonMapper.objToString(after));
        sysLogWithBLOBs.setOperator(RequestHolder.getCurrentUser().getUsername());
        sysLogWithBLOBs.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        sysLogWithBLOBs.setOperateTime(new Date());
        sysLogWithBLOBs.setStatus(0);
        sysLogMapper.insertSelective(sysLogWithBLOBs);
    }

    public void saveRoleAclLog(int roleId, List<Integer> before, List<Integer> after) {
        SysLogWithBLOBs sysLogWithBLOBs = new SysLogWithBLOBs();
        sysLogWithBLOBs.setType(LogType.TYPE_ROLE_ACL);
        sysLogWithBLOBs.setTargetId(roleId);
        sysLogWithBLOBs.setOldValue(before == null ? "" : JsonMapper.objToString(before));
        sysLogWithBLOBs.setNewValue(after == null ? "" : JsonMapper.objToString(after));
        sysLogWithBLOBs.setOperator(RequestHolder.getCurrentUser().getUsername());
        sysLogWithBLOBs.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        sysLogWithBLOBs.setOperateTime(new Date());
        sysLogWithBLOBs.setStatus(0);
        sysLogMapper.insertSelective(sysLogWithBLOBs);
    }

    public void saveRoleUserLog(int roleId, List<Integer> before, List<Integer> after) {
        SysLogWithBLOBs sysLogWithBLOBs = new SysLogWithBLOBs();
        sysLogWithBLOBs.setType(LogType.TYPE_ROLE_USER);
        sysLogWithBLOBs.setTargetId(roleId);
        sysLogWithBLOBs.setOldValue(before == null ? "" : JsonMapper.objToString(before));
        sysLogWithBLOBs.setNewValue(after == null ? "" : JsonMapper.objToString(after));
        sysLogWithBLOBs.setOperator(RequestHolder.getCurrentUser().getUsername());
        sysLogWithBLOBs.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        sysLogWithBLOBs.setOperateTime(new Date());
        sysLogWithBLOBs.setStatus(0);
        sysLogMapper.insertSelective(sysLogWithBLOBs);
    }
}
