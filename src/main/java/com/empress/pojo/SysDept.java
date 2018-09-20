package com.empress.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author Hystar
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SysDept {
    /**
     * 部门ID
     */
    private Integer id;

    /**
     * 部门名称
     */
    private String name;

    /**
     * 上级部门的ID
     */
    private Integer parentId;

    /**
     * 部门的层级
     */
    private String level;

    /**
     * 部门在当前层级下的顺序，由小到大
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