package com.empress.pojo;

import lombok.Data;

import java.util.Date;

/**
 * @author Hystar
 */
@Data
public class SysUser {
    private Integer id;

    private String username;

    private String telephone;

    private String mail;

    private String password;

    private Integer deptId;

    private Integer status;

    private String remark;

    private String operator;

    private Date operateTime;

    private String operateIp;

    public SysUser(Integer id, String username, String telephone, String mail, String password, Integer deptId, Integer status, String remark, String operator, Date operateTime, String operateIp) {
        this.id = id;
        this.username = username;
        this.telephone = telephone;
        this.mail = mail;
        this.password = password;
        this.deptId = deptId;
        this.status = status;
        this.remark = remark;
        this.operator = operator;
        this.operateTime = operateTime;
        this.operateIp = operateIp;
    }

    public SysUser() {
        super();
    }

}