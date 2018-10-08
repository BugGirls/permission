package com.empress.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 登录的用户信息
 *
 * @author Hystar
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SysUser {
    /**
     * 用户ID
     */
    private Integer id;

    /**
     * 用户名称
     */
    private String username;

    /**
     * 手机号
     */
    private String telephone;

    /**
     * 邮箱
     */
    private String mail;

    /**
     * 加密后的密码
     */
    private String password;

    /**
     * 用户所在部门的ID
     */
    private Integer deptId;

    /**
     * 用户状态：1-正常，0-冻结，2：删除状态
     */
    private Integer status;

    /**
     * 备注
     */
    private String remark;

    /**
     * 操作人
     */
    private String operator;

    /**
     * 最后一次操作时间
     */
    private Date operateTime;

    /**
     * 最后一次更新操作者的IP地址
     */
    private String operateIp;

}