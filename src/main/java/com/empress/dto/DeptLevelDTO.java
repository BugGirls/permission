package com.empress.dto;

import com.empress.pojo.SysDept;
import com.google.common.collect.Lists;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.beans.BeanUtils;

import java.util.List;

/**
 * 部门层级树DTO
 *
 * @author Hystar
 * @date 2018/9/6
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class DeptLevelDTO extends SysDept {

    private List<DeptLevelDTO> deptLevelDTOList = Lists.newArrayList();

    /**
     * 将父类SysDept 中的属性值拷贝到子类DeptLevelDTO 对象中
     *
     * @param sysDept
     * @return
     */
    public static DeptLevelDTO adapt(SysDept sysDept) {
        DeptLevelDTO deptLevelDTO = new DeptLevelDTO();
        BeanUtils.copyProperties(sysDept, deptLevelDTO);
        return deptLevelDTO;
    }
}
