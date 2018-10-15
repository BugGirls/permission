package com.empress.dao;

import com.empress.beans.PageQuery;
import com.empress.pojo.SysAcl;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Hystar
 */
public interface SysAclMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(SysAcl record);

    int insertSelective(SysAcl record);

    SysAcl selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysAcl record);

    int updateByPrimaryKey(SysAcl record);

    /**
     * 统计 当前权限模块、在当前层级下、权限名称相同、的个数
     * 如果是更新权限时判断是否存在重复的权限：则id不为空，则查询范围为当前权限模块下当前权限以外的其他所有权限
     * 如果是新增权限时判断是否存在重复的权限：则id为空，则查询当前权限模块下的所有权限
     *
     * @param alcModuleId
     * @param name
     * @param id
     * @return
     */
    int countByNameAndAclModuleId(@Param(value = "aclModuleId") Integer alcModuleId, @Param(value = "name") String name, @Param(value = "id") Integer id);

    /**
     * 获取当前权限模块下的权限数量
     *
     * @param aclModuleId
     * @return
     */
    int countByAclModuleId(@Param(value = "aclModuleId") Integer aclModuleId);

    /**
     * 获取当前权限模块下的权限列表
     *
     * @param aclModuleId
     * @param pageQuery
     * @return
     */
    List<SysAcl> getPageByAclModuleId(@Param(value = "aclModuleId") Integer aclModuleId, @Param(value = "pageQuery") PageQuery pageQuery);

    /**
     * 获取所有权限
     *
     * @return
     */
    List<SysAcl> getAll();

    /**
     * 通过权限ID列表 获取权限列表
     *
     * @param roleIdList
     * @return
     */
    List<SysAcl> getByIdList(@Param(value = "roleIdList") List<Integer> roleIdList);

    List<SysAcl> getByUrl(@Param(value = "url") String url);
}