package com.empress.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 角色-权限
 *
 * @author Hystar
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SysRoleAcl {

    private Integer id;

    private Integer roleId;

    private Integer aclId;

    private String operator;

    private Date operateTime;

    private String operateIp;

}