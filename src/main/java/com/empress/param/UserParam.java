package com.empress.param;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * 用户登录信息参数
 *
 * @author Hystar
 * @date 2018/9/30 0030
 */
@Data
public class UserParam {

    /**
     * 用户ID
     */
    private Integer id;

    /**
     * 用户名称
     */
    @NotBlank(message = "用户名不可以为空")
    @Length(min = 0, max = 32, message = "用户名长度需要在20个字以内")
    private String username;

    /**
     * 手机号
     */
    @NotBlank(message = "手机号不可以为空")
    @Length(min = 1, max = 13, message = "手机号的长度需要在13个字以内")
    private String telephone;

    /**
     * 邮箱
     */
    @NotBlank(message = "邮箱不可以为空")
    @Length(min = 5, max = 50, message = "邮箱的长度需要在50个字以内")
    private String mail;

    /**
     * 用户所在部门的ID
     */
    @NotNull(message = "必须提供用户所在的部门")
    private Integer deptId;

    /**
     * 用户状态：1-正常，0-冻结，2：删除状态
     */
    @NotNull(message = "必须指定用户的状态")
    @Min(value = 0, message = "用户状态不合法")
    @Max(value = 2, message = "用户状态不合法")
    private Integer status;

    /**
     * 备注
     */
    @Length(max = 150, message = "备注的长度不能超过150个字")
    private String remark;

}
