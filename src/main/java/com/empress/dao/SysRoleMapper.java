package com.empress.dao;

import com.empress.pojo.SysRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Hystar
 */
public interface SysRoleMapper {


    int deleteByPrimaryKey(Integer id);

    int insert(SysRole record);

    int insertSelective(SysRole record);

    SysRole selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysRole record);

    int updateByPrimaryKey(SysRole record);

    /**
     * 获取所有角色
     *
     * @return
     */
    List<SysRole> getAll();

    /**
     * 统计 当前角色的、角色名称相同的个数
     * 如果是更新角色时判断是否存在相同的角色：则id不为空，则查询范围为当前角色以外的其他所有角色
     * 如果是新增角色时判断是否存在相同的角色：则id为空，则查询所有角色
     *
     * @param name
     * @param id
     * @return
     */
    int countByName(@Param(value = "name") String name, @Param(value = "id") Integer id);

    /**
     * 通过角色ID列表获取角色信息列表
     *
     * @param idList
     * @return
     */
    List<SysRole> getByIdList(@Param(value = "idList") List<Integer> idList);
}