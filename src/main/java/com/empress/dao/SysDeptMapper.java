package com.empress.dao;

import com.empress.pojo.SysDept;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Hystar
 */
public interface SysDeptMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(SysDept record);

    int insertSelective(SysDept record);

    SysDept selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysDept record);

    int updateByPrimaryKey(SysDept record);

    /**
     * 获取所有部门记录
     *
     * @return
     */
    List<SysDept> getAllDept();

    /**
     * 通过传入的level获取当前部门以及当前部门下子部门的部门列表
     * 由于level的格式为0/0.1/0.1.2 ...
     * 所以当传入的level为0.1时，则可以获取的层级为0.1的部门和前缀为0.1的子部门
     *
     * @param level
     * @return
     */
    List<SysDept> getChildDeptListByLevel(@Param(value = "level") String level);

    /**
     * 批量更新部门信息
     *
     * @param sysDeptList
     */
    void batchUpdateLevel(@Param(value = "sysDeptList") List<SysDept> sysDeptList);

    /**
     * 统计 当前部门、在当前层级下、部门名称相同、的个数
     * 如果是更新部门时判断是否存在重复的部门：则id不为空，则查询范围为当前层级下当前部门以外的其他所有部门
     * 如果是新增部门时判断是否存在重复的部门：则id为空，则查询当前层级下的所有部门
     *
     * @param parentId
     * @param name
     * @param id
     * @return
     */
    int countByNameAndParentId(@Param(value = "parentId") Integer parentId, @Param(value = "name") String name, @Param(value = "id") Integer id);

    /**
     * 计算当前上级部门的数量
     *
     * @param parentId
     * @return
     */
    int countByParentId(@Param(value = "parentId") Integer parentId);
}