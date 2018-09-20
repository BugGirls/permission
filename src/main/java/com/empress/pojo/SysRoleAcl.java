package com.empress.pojo;

import lombok.Data;

import java.util.Date;

/**
 * @author Hystar
 */
@Data
public class SysRoleAcl {
    private Integer id;

    private Integer roleId;

    private Integer aclId;

    private String operator;

    private Date operateTime;

    private String operateIp;

    public SysRoleAcl(Integer id, Integer roleId, Integer aclId, String operator, Date operateTime, String operateIp) {
        this.id = id;
        this.roleId = roleId;
        this.aclId = aclId;
        this.operator = operator;
        this.operateTime = operateTime;
        this.operateIp = operateIp;
    }

    public SysRoleAcl() {
        super();
    }

}