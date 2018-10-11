package com.empress.dao;

import com.empress.pojo.SysRoleUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Hystar
 */
public interface SysRoleUserMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(SysRoleUser record);

    int insertSelective(SysRoleUser record);

    SysRoleUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysRoleUser record);

    int updateByPrimaryKey(SysRoleUser record);

    /**
     * 通过userId获取角色列表
     *
     * @param userId
     * @return
     */
    List<Integer> getRoleIdListByUserId(@Param(value = "userId") Integer userId);

    /**
     * 通过角色ID获取用户ID列表
     *
     * @param roleId
     * @return
     */
    List<Integer> getUserIdListByRoleId(@Param(value = "roleId") int roleId);

    /**
     * 通过角色ID列表 获取 用户ID列表
     *
     * @param roleIdList
     * @return
     */
    List<Integer> getUserIdListByRoleIdList(@Param(value = "roleIdList") List<Integer> roleIdList);

    /**
     * 通过角色ID删除角色-用户信息
     *
     * @param roleId
     */
    void deleteByRoleId(@Param(value = "roleId") Integer roleId);

    /**
     * 批量插入角色-用户数据
     *
     * @param roleUserList
     */
    void batchInsert(@Param(value = "roleUserList") List<SysRoleUser> roleUserList);

}