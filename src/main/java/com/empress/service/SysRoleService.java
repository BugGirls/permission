package com.empress.service;

import com.empress.common.RequestHolder;
import com.empress.dao.SysRoleAclMapper;
import com.empress.dao.SysRoleMapper;
import com.empress.dao.SysRoleUserMapper;
import com.empress.exception.ParamException;
import com.empress.param.RoleParam;
import com.empress.pojo.SysRole;
import com.empress.util.BeanValidator;
import com.empress.util.IpUtil;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 角色 Service
 *
 * @author Hystar
 * @date 2018/10/9
 */
@Service
public class SysRoleService {

    @Resource
    private SysRoleMapper sysRoleMapper;

    @Resource
    private SysRoleUserMapper sysRoleUserMapper;

    @Resource
    private SysRoleAclMapper sysRoleAclMapper;

    /**
     * 新增角色
     *
     * @param param
     */
    public void save(RoleParam param) {
        // 参数校验
        BeanValidator.check(param);

        // 判断角色名称是否重复（判断范围：所有记录）
        if (checkExist(param.getName(), param.getId())) {
            throw new ParamException("角色名称已经存在");
        }

        // 新增角色
        SysRole sysRole = SysRole.builder().name(param.getName()).status(param.getStatus()).type(param.getType()).remark(param.getRemark()).build();
        sysRole.setOperator(RequestHolder.getCurrentUser().getUsername());
        sysRole.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        sysRole.setOperateTime(new Date());

        sysRoleMapper.insertSelective(sysRole);
    }

    /**
     * 更新角色信息
     *
     * @param param
     */
    public void update(RoleParam param) {
        // 参数校验
        BeanValidator.check(param);

        // 判断角色名称是否重复（判断范围：所有记录）
        if (checkExist(param.getName(), param.getId())) {
            throw new ParamException("角色名称已经存在");
        }

        SysRole before = sysRoleMapper.selectByPrimaryKey(param.getId());
        Preconditions.checkNotNull(before, "待更新的角色不存在");

        SysRole after = SysRole.builder().id(param.getId()).name(param.getName()).status(param.getStatus()).type(param.getType()).remark(param.getRemark()).build();
        after.setOperator(RequestHolder.getCurrentUser().getUsername());
        after.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        after.setOperateTime(new Date());

        sysRoleMapper.updateByPrimaryKeySelective(after);
    }

    /**
     * 获取所有角色信息列表
     *
     * @return
     */
    public List<SysRole> getAll() {
        return sysRoleMapper.getAll();
    }

    /**
     * 校验角色名称是否存在
     *
     * @param name
     * @param id
     * @return
     */
    private boolean checkExist(String name, Integer id) {
        return sysRoleMapper.countByName(name, id) > 0;
    }

    /**
     * 获取当前用户的角色列表
     *
     * @param userId
     * @return
     */
    public List<SysRole> getRoleListByUserId(int userId) {
        List<Integer> roleIds = sysRoleUserMapper.getRoleIdListByUserId(userId);
        if (CollectionUtils.isEmpty(roleIds)) {
            return Lists.newArrayList();
        }
        List<SysRole> sysRoles = sysRoleMapper.getByIdList(roleIds);
        return sysRoles;
    }

    /**
     * 获取当前权限所对应的角色列表
     *
     * @param aclId
     * @return
     */
    public List<SysRole> getRoleListByAclId(int aclId) {
        List<Integer> roleIds = sysRoleAclMapper.getRoleIdListByAclId(aclId);
        if (CollectionUtils.isEmpty(roleIds)) {
            return Lists.newArrayList();
        }
        List<SysRole> sysRoles = sysRoleMapper.getByIdList(roleIds);
        return sysRoles;
    }


}
