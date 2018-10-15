package com.empress.pojo;

import java.util.Date;

/**
 * @author Hystar
 */
public class SysLogWithBLOBs extends SysLog {

    private String oldValue;

    private String newValue;

    public SysLogWithBLOBs(Integer id, Integer type, Integer targetId, Integer status, String operator, Date operateTime, String operateIp, String oldValue, String newValue) {
        super(id, type, targetId, status, operator, operateTime, operateIp);
        this.oldValue = oldValue;
        this.newValue = newValue;
    }

    public SysLogWithBLOBs() {
        super();
    }

    public String getOldValue() {
        return oldValue;
    }

    public void setOldValue(String oldValue) {
        this.oldValue = oldValue == null ? null : oldValue.trim();
    }

    public String getNewValue() {
        return newValue;
    }

    public void setNewValue(String newValue) {
        this.newValue = newValue == null ? null : newValue.trim();
    }
}