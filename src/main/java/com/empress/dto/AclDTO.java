package com.empress.dto;

import com.empress.pojo.SysAcl;
import lombok.Data;
import org.springframework.beans.BeanUtils;

/**
 * @author Hystar
 * @date 2018/10/10 0010
 */
@Data
public class AclDTO extends SysAcl {

    /**
     * 是否要默认选中
     */
    private boolean checked = false;

    /**
     * 是否有权限操作
     */
    private boolean hasAcl = false;

    public static AclDTO adapt(SysAcl sysAcl) {
        AclDTO aclDTO = new AclDTO();
        BeanUtils.copyProperties(sysAcl, aclDTO);
        return aclDTO;
    }
}
