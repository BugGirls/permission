package com.empress.dto;

import com.empress.pojo.SysAclModule;
import com.google.common.collect.Lists;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.beans.BeanUtils;

import java.util.List;

/**
 * @author Hystar
 * @date 2018/10/9
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class AclModuleLevelDTO extends SysAclModule {

    /**
     * 当前权限模块的下级权限模块列表
     */
    private List<AclModuleLevelDTO> aclModuleLevelDTOList = Lists.newArrayList();

    /**
     * 当前权限模块下的权限列表
     */
    private List<AclDTO> aclDTOList = Lists.newArrayList();

    /**
     * 将父类SysAclModule 中的属性值拷贝到子类AclModuleLevelDTO 对象中
     *
     * @param sysAclModule
     * @return
     */
    public static AclModuleLevelDTO adapt(SysAclModule sysAclModule) {
        AclModuleLevelDTO aclModuleLevelDTO = new AclModuleLevelDTO();
        BeanUtils.copyProperties(sysAclModule, aclModuleLevelDTO);
        return aclModuleLevelDTO;
    }
}
