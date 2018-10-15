package com.empress.service;

import com.empress.common.RequestHolder;
import com.empress.dao.SysRoleAclMapper;
import com.empress.pojo.SysRoleAcl;
import com.empress.util.IpUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * 角色与权限 Service
 *
 * @author Hystar
 * @date 2018/10/11 0011
 */
@Service
public class SysRoleAclService {

    @Resource
    private SysRoleAclMapper sysRoleAclMapper;

    @Resource
    private SysLogService sysLogService;

    /**
     * 修改角色与权限信息
     *
     * @param roleId
     * @param aclIdList
     */
    public void changeRoleAcls(Integer roleId, List<Integer> aclIdList) {
        // 获取该角色原始的权限ID列表
        List<Integer> originAclIdList = sysRoleAclMapper.getAclIdListByRoleIdList(Lists.newArrayList(roleId));

        // 判断两个集合是否相等，即当用户什么都没有改变时，无需执行保存操作，直接返回
        if (originAclIdList.size() == aclIdList.size()) {
            Set<Integer> originAclIdSet = Sets.newHashSet(originAclIdList);
            Set<Integer> aclIdSet = Sets.newHashSet(aclIdList);
            originAclIdSet.removeAll(aclIdSet);
            if (CollectionUtils.isEmpty(originAclIdSet)) {
                return;
            }
        }

        // 更新操作
        updateRoleAcls(roleId, aclIdList);

        sysLogService.saveRoleAclLog(roleId, originAclIdList, aclIdList);
    }

    /**
     * 更新角色权限信息
     *
     * @param roleId
     * @param aclIdList
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateRoleAcls(int roleId, List<Integer> aclIdList) {
        // 先删除之前的角色权限数据
        sysRoleAclMapper.deleteByRoleId(roleId);

        // 判断是否为空
        if (CollectionUtils.isEmpty(aclIdList)) {
            return;
        }

        // 批量插入角色权限数据
        List<SysRoleAcl> roleAclList = Lists.newArrayList();
        for (Integer aclId : aclIdList) {
            SysRoleAcl roleAcl = SysRoleAcl.builder().roleId(roleId).aclId(aclId).operateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest())).
                    operator(RequestHolder.getCurrentUser().getUsername()).operateTime(new Date()).build();
            roleAclList.add(roleAcl);
        }
        sysRoleAclMapper.batchInsert(roleAclList);
    }
}
