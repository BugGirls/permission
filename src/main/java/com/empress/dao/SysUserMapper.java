package com.empress.dao;

import com.empress.beans.PageQuery;
import com.empress.pojo.SysUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Hystar
 */
public interface SysUserMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(SysUser record);

    int insertSelective(SysUser record);

    SysUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysUser record);

    int updateByPrimaryKey(SysUser record);

    /**
     * 通过关键字获取用户信息，关键字为手机号或邮箱
     *
     * @param keyword
     * @return
     */
    SysUser findByKeyword(@Param(value = "keyword") String keyword);

    /**
     * 传入id，为更新操作，用于判断当前用户的其他用户的邮箱是否已经存在
     * 未传入id，为新增操作，用于判断当前邮箱是否已经存在
     *
     * @param mail
     * @param id
     * @return
     */
    int countByMail(@Param(value = "mail") String mail, @Param(value = "id") Integer id);

    /**
     * 传入id，为更新操作，用于判断当前用户的其他用户的手机号是否已经存在
     * 未传入id，为新增操作，用于判断当前手机号是否已经存在
     *
     * @param telephone
     * @param id
     * @return
     */
    int countByTelephone(@Param(value = "telephone") String telephone, @Param(value = "id") Integer id);

    /**
     * 获取当前部门下的用户数量
     *
     * @param deptId
     * @return
     */
    int countByDeptId(@Param(value = "deptId") int deptId);

    /**
     * 获取当前部门下的用户列表
     *
     * @param deptId
     * @param pageQuery
     * @return
     */
    List<SysUser> getPageByDeptId(@Param(value = "deptId") int deptId, @Param(value = "pageQuery") PageQuery pageQuery);

    /**
     * 通过Id列表获取用户列表
     *
     * @param idList
     * @return
     */
    List<SysUser> getByIdList(@Param(value = "idList") List<Integer> idList);

    /**
     * 获取所有用户列表
     *
     * @return
     */
    List<SysUser> getAll();

}