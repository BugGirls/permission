package com.empress.pojo;

import lombok.Data;

import java.util.Date;

/**
 * @author Hystar
 */
@Data
public class SysLog {
    private Integer id;

    private Integer type;

    private Integer targetId;

    private Integer status;

    private String operator;

    private Date operateTime;

    private String operateIp;

    public SysLog(Integer id, Integer type, Integer targetId, Integer status, String operator, Date operateTime, String operateIp) {
        this.id = id;
        this.type = type;
        this.targetId = targetId;
        this.status = status;
        this.operator = operator;
        this.operateTime = operateTime;
        this.operateIp = operateIp;
    }

    public SysLog() {
        super();
    }

}