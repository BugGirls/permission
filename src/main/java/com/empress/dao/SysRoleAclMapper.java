package com.empress.dao;

import com.empress.pojo.SysRoleAcl;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Hystar
 */
public interface SysRoleAclMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(SysRoleAcl record);

    int insertSelective(SysRoleAcl record);

    SysRoleAcl selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysRoleAcl record);

    int updateByPrimaryKey(SysRoleAcl record);

    /**
     * 通过角色ID列表 获取 权限ID列表
     *
     * @param roleIdList
     * @return
     */
    List<Integer> getAclIdListByRoleIdList(@Param(value = "roleIdList") List<Integer> roleIdList);

    /**
     * 通过角色ID删除角色-权限信息
     *
     * @param roleId
     */
    void deleteByRoleId(@Param(value = "roleId") int roleId);

    /**
     * 批量插入角色权限信息
     *
     * @param roleAclList
     */
    void batchInsert(@Param(value = "roleAclList") List<SysRoleAcl> roleAclList);

    /**
     * 通过权限ID获取角色ID列表
     *
     * @param aclId
     * @return
     */
    List<Integer> getRoleIdListByAclId(@Param(value = "aclId") Integer aclId);
}