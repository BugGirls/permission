package com.empress.pojo;

import lombok.Data;

import java.util.Date;

/**
 * @author Hystar
 */
@Data
public class SysAclModule {
    private Integer id;

    private String name;

    private Integer parentId;

    private String level;

    private Integer status;

    private Integer seq;

    private String remark;

    private String operator;

    private Date operateTime;

    private String operateIp;

    public SysAclModule(Integer id, String name, Integer parentId, String level, Integer status, Integer seq, String remark, String operator, Date operateTime, String operateIp) {
        this.id = id;
        this.name = name;
        this.parentId = parentId;
        this.level = level;
        this.status = status;
        this.seq = seq;
        this.remark = remark;
        this.operator = operator;
        this.operateTime = operateTime;
        this.operateIp = operateIp;
    }

    public SysAclModule() {
        super();
    }

}