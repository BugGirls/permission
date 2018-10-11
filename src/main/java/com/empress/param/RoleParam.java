package com.empress.param;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * 角色
 *
 * @author Hystar
 * @date 2018/10/10 0010
 */
@Data
public class RoleParam {

    /**
     * 角色ID
     */
    private Integer id;

    /**
     * 角色名称
     */
    @NotBlank(message = "角色名称不能为空")
    @Length(min = 2, max = 20, message = "角色名称需要在2~20个字之间")
    private String name;

    /**
     * 角色类型（扩展使用）：1-管理员角色，2-其他
     */
    @NotNull(message = "角色类型不能为空")
    @Min(value = 1, message = "角色类型不合法")
    @Max(value = 2, message = "角色类型不合法")
    private Integer type;

    /**
     * 状态：1-正常，0-冻结
     */
    @NotNull(message = "必须指定角色的状态")
    @Min(value = 0, message = "角色状态不合法")
    @Max(value = 1, message = "角色状态不合法")
    private Integer status;

    /**
     * 备注
     */
    @Length(min = 0, max = 100, message = "备注的长度需要在100个字以内")
    private String remark;

}
