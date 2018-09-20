package com.empress.pojo;

import lombok.Data;

import java.util.Date;

/**
 * @author Hystar
 */
@Data
public class SysRoleUser {
    private Integer id;

    private Integer roleId;

    private Integer userId;

    private String operator;

    private Date operateTime;

    private String operateIp;

    public SysRoleUser(Integer id, Integer roleId, Integer userId, String operator, Date operateTime, String operateIp) {
        this.id = id;
        this.roleId = roleId;
        this.userId = userId;
        this.operator = operator;
        this.operateTime = operateTime;
        this.operateIp = operateIp;
    }

    public SysRoleUser() {
        super();
    }

}