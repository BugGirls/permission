package com.empress.pojo;

import lombok.Data;

import java.util.Date;

/**
 * @author Hystar
 */
@Data
public class SysRole {
    private Integer id;

    private String name;

    private Integer type;

    private Integer status;

    private String remark;

    private String operator;

    private Date operateTime;

    private String operateIp;

    public SysRole(Integer id, String name, Integer type, Integer status, String remark, String operator, Date operateTime, String operateIp) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.status = status;
        this.remark = remark;
        this.operator = operator;
        this.operateTime = operateTime;
        this.operateIp = operateIp;
    }

    public SysRole() {
        super();
    }

}