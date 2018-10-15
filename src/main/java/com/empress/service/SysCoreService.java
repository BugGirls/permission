package com.empress.service;

import com.empress.common.RequestHolder;
import com.empress.dao.SysAclMapper;
import com.empress.dao.SysRoleAclMapper;
import com.empress.dao.SysRoleUserMapper;
import com.empress.pojo.SysAcl;
import com.empress.pojo.SysUser;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 获取相关角色和权限
 *
 * @author Hystar
 * @date 2018/10/10 0010
 */
@Service
public class SysCoreService {

    @Resource
    private SysAclMapper sysAclMapper;

    @Resource
    private SysRoleUserMapper sysRoleUserMapper;

    @Resource
    private SysRoleAclMapper sysRoleAclMapper;

    /**
     * 获取当前用户所拥有的权限列表
     *
     * @return
     */
    public List<SysAcl> getCurrentUserAclList() {
        int userId = RequestHolder.getCurrentUser().getId();
        return getUserAclList(userId);
    }

    /**
     * 获取当前角色所拥有的权限列表
     *
     * @param roleId
     * @return
     */
    public List<SysAcl> getRoleAclList(int roleId) {
        // 获取当前角色下的权限ID列表
        List<Integer> aclIdList = sysRoleAclMapper.getAclIdListByRoleIdList(Lists.newArrayList(roleId));
        if (CollectionUtils.isEmpty(aclIdList)) {
            // 如果该角色没有分配任何权限，则直接返回空列表
            return Lists.newArrayList();
        }
        // 返回权限列表
        return sysAclMapper.getByIdList(aclIdList);
    }

    /**
     * 查询某个用户已分配的所有权限
     *
     * @param userId
     * @return
     */
    public List<SysAcl> getUserAclList(int userId) {
        if (isSuperAdmin()) {
            // 如果是超级管理员，则直接取出所有的权限
            return sysAclMapper.getAll();
        }

        // 如果不是超级管理员，获取用户已经分配的角色ID
        List<Integer> roleIdList = sysRoleUserMapper.getRoleIdListByUserId(userId);
        if (CollectionUtils.isEmpty(roleIdList)) {
            // 如果用户没有分配任何角色，则直接返回空列表
            return Lists.newArrayList();
        }

        // 通过角色ID列表获取权限ID列表
        List<Integer> aclIdList = sysRoleAclMapper.getAclIdListByRoleIdList(roleIdList);
        if (CollectionUtils.isEmpty(aclIdList)) {
            // 如果角色没有被分配权限，则直接返回空列表
            return Lists.newArrayList();
        }

        // 通过权限ID列表获取权限列表
        return sysAclMapper.getByIdList(aclIdList);
    }

    /**
     * 判断是否为超级管理员
     *
     * @return
     */
    public boolean isSuperAdmin() {
        // 这里定义了一个测试的超级管理员规则，实际中要根据项目进行修改，可以是配置文件获取，页可以指定某个用户，页可以指定某个角色
        SysUser sysUser = RequestHolder.getCurrentUser();
        if (sysUser.getMail().contains("admin")) {
            return true;
        }
        return false;
    }

    /**
     * 获取当前登录用户是否有权限访问当前URL
     *
     * @param url
     * @return
     */
    public boolean hasUrlAcl(String url) {
        // 判断是否为超级管理员
        if (isSuperAdmin()) {
            return true;
        }

        // 获取当前URL对应的权限点
        List<SysAcl> aclList = sysAclMapper.getByUrl(url);
        if (CollectionUtils.isEmpty(aclList)) {
            // 如果没有权限点，说明该URL不做验证
            return true;
        }

        // 获取当前登录用户的权限列表
        List<SysAcl> userAclList = getCurrentUserAclList();
        // 权限ID集合
        Set<Integer> userAclIdSet = userAclList.stream().map(sysAcl -> sysAcl.getId()).collect(Collectors.toSet());

        // 权限是否有效标志
        boolean hasValidAcl = false;
        // 规则：只要有一个权限点有权限，那么我们就认为有访问权限
        for (SysAcl sysAcl : aclList) {
            // 判断一个用户是否具有某个权限点的访问权限
            if (sysAcl == null || sysAcl.getStatus() != 1) {
                // 权限点无效
                continue;
            }
            hasValidAcl = true;
            // 有效
            if (userAclIdSet.contains(sysAcl.getId())) {
                return true;
            }

        }

        // 如果所有权限都无效，则返回true
        if (!hasValidAcl) {
            return true;
        }

        return false;
    }
}
