package com.empress.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 日志
 *
 * @author Hystar
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SysLog {

    private Integer id;

    /**
     * 权限更新的类型：1-部门，2-用户，3-权限模块，4-权限，5-角色，6-角色用户关系，7-角色权限关系
     */
    private Integer type;

    /**
     * 基于type后指定的对象ID，比如用户、权限、角色等表的主键
     */
    private Integer targetId;

    /**
     * 当前是否复原过：0-没有，1-复原过
     */
    private Integer status;

    private String operator;

    private Date operateTime;

    private String operateIp;

}