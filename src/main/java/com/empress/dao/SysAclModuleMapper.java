package com.empress.dao;

import com.empress.pojo.SysAclModule;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Hystar
 */
public interface SysAclModuleMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(SysAclModule record);

    int insertSelective(SysAclModule record);

    SysAclModule selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysAclModule record);

    int updateByPrimaryKey(SysAclModule record);

    /**
     * 统计 当前权限模块、在当前层级下、模块名称相同、的个数
     * 如果是更新权限模块时判断是否存在重复的权限模块：则aclModuleId不为空，则查询范围为当前层级下当前权限模块以外的其他所有权限模块
     * 如果是新增权限模块时判断是否存在重复的权限模块：则aclModuleId为空，则查询当前层级下的所有权限模块
     *
     * @param parentId
     * @param aclModuleName
     * @param aclModuleId
     * @return
     */
    int countByNameAndParentId(@Param(value = "parentId") Integer parentId, @Param(value = "aclModuleName") String aclModuleName, @Param(value = "aclModuleId") Integer aclModuleId);

    /**
     * 通过传入的level获取当前权限模块以及当前权限模块下子模块的模块列表
     * 由于level的格式为0/0.1/0.1.2 ...
     * 所以当传入的level为0.1时，则可以获取的层级为0.1的模块和前缀为0.1的子模块
     *
     * @param level
     * @return
     */
    List<SysAclModule> getChildAclModuleListByLevel(@Param(value = "level") String level);

    /**
     * 批量更新部门信息
     *
     * @param sysAclModuleList
     */
    void batchUpdateLevel(@Param(value = "sysAclModuleList") List<SysAclModule> sysAclModuleList);

    /**
     * 获取全部的权限模块记录
     *
     * @return
     */
    List<SysAclModule> getAllAclModule();

    /**
     * 计算当前权限模块的下级模块的数量
     *
     * @param aclModuleId
     * @return
     */
    int countByParentId(@Param(value = "aclModuleId") Integer aclModuleId);

}