package com.empress.pojo;

import lombok.*;

import java.util.Date;

/**
 * 权限，挂载在权限模块下
 *
 * @author Hystar
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class SysAcl {

    /**
     * 权限ID
     */
    private Integer id;

    /**
     * 权限码
     */
    private String code;

    /**
     * 权限名称
     */
    private String name;

    /**
     * 权限所在的权限模块ID
     */
    private Integer aclModuleId;

    /**
     * 请求的URL，可以填写正则表达式
     */
    private String url;

    /**
     * 类型：1-菜单，2-按钮，3-其他
     */
    private Integer type;

    /**
     * 状态：1-正常，0-冻结
     */
    private Integer status;

    /**
     * 权限模块在当前层级下的顺序，由小到大
     */
    private Integer seq;

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