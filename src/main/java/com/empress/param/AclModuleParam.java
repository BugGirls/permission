package com.empress.param;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * 权限模块参数
 *
 * @author Hystar
 * @date 2018/10/9
 */
@Data
public class AclModuleParam {

    /**
     * 权限模块ID
     */
    private Integer id;

    /**
     * 权限模块名称
     */
    @NotBlank(message = "权限模块名称不能为空")
    @Length(min = 2, max = 30, message = "权限名称长度在2-30个字之间")
    private String name;

    /**
     * 上级权限模块的ID
     */
    private Integer parentId = 0;

    /**
     * 状态：1-正常，0-冻结
     */
    @NotNull(message = "权限模块状态不能为空")
    @Min(value = 0, message = "权限模块状态不合法")
    @Max(value = 1, message = "权限模块状态不合法")
    private Integer status;

    /**
     * 权限模块在当前层级下的顺序，由小到大
     */
    @NotNull(message = "权限模块展示顺序不能为空")
    private Integer seq;

    /**
     * 备注
     */
    @Length(max = 100, message = "备注需要在100个字以内")
    private String remark;
}
