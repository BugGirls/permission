package com.empress.pojo;

import lombok.Data;

import java.util.Date;

/**
 * @author Hystar
 */
@Data
public class SysAcl {
    private Integer id;

    private String code;

    private String name;

    private Integer aclModuleId;

    private String url;

    private Integer type;

    private Integer status;

    private Integer seq;

    private String remark;

    private String operator;

    private Date operateTime;

    private String operateIp;

    public SysAcl(Integer id, String code, String name, Integer aclModuleId, String url, Integer type, Integer status, Integer seq, String remark, String operator, Date operateTime, String operateIp) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.aclModuleId = aclModuleId;
        this.url = url;
        this.type = type;
        this.status = status;
        this.seq = seq;
        this.remark = remark;
        this.operator = operator;
        this.operateTime = operateTime;
        this.operateIp = operateIp;
    }

    public SysAcl() {
        super();
    }

}