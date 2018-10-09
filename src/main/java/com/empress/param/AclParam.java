package com.empress.param;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author Hystar
 * @date 2018/10/9
 */
@Data
public class AclParam {

    /**
     * 权限ID
     */
    private Integer id;

    /**
     * 权限名称
     */
    @NotBlank(message = "权限点名称不能为空")
    @Length(min = 2, max = 20, message = "权限名称长度需要在2~20个字之间")
    private String name;

    /**
     * 权限所在的权限模块ID
     */
    @NotNull(message = "必须指定权限模块")
    private Integer aclModuleId;

    /**
     * 请求的URL，可以填写正则表达式
     */
    @Length(min = 6, max = 100, message = "权限URL长度需要在6~100字符之间")
    private String url;

    /**
     * 类型：1-菜单，2-按钮，3-其他
     */
    @NotNull(message = "必须指定权限的类型")
    @Min(value = 1, message = "权限类型不合法")
    @Max(value = 3, message = "权限类型不合法")
    private Integer type;

    /**
     * 状态：1-正常，0-冻结
     */
    @NotNull(message = "必须指定权限的状态")
    @Min(value = 0, message = "权限状态不合法")
    @Max(value = 1, message = "权限状态不合法")
    private Integer status;

    /**
     * 权限模块在当前层级下的顺序，由小到大
     */
    @NotNull(message = "必须指定权限的展示顺序")
    private Integer seq;

    /**
     * 备注
     */
    @Length(min = 0, max = 100, message = "权限备注的长度需要在100个字以内")
    private String remark;


}
