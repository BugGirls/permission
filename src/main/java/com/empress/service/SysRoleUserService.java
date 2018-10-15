package com.empress.service;

import com.empress.common.RequestHolder;
import com.empress.dao.SysRoleUserMapper;
import com.empress.dao.SysUserMapper;
import com.empress.pojo.SysRoleUser;
import com.empress.pojo.SysUser;
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
 * 角色-用户 Service
 *
 * @author Hystar
 * @date 2018/10/11 0011
 */
@Service
public class SysRoleUserService {

    @Resource
    private SysRoleUserMapper sysRoleUserMapper;

    @Resource
    private SysUserMapper sysUserMapper;

    @Resource
    private SysLogService sysLogService;

    /**
     * 获取当前角色所对应的用户列表
     *
     * @param roleId
     * @return
     */
    public List<SysUser> getListByRoleId(int roleId) {
        // 通过角色ID获取用户ID列表
        List<Integer> userIdList = sysRoleUserMapper.getUserIdListByRoleId(roleId);
        if (CollectionUtils.isEmpty(userIdList)) {
            return Lists.newArrayList();
        }
        List<SysUser> sysUserList = sysUserMapper.getByIdList(userIdList);
        return sysUserList;
    }

    /**
     * 修改角色与权限信息
     *
     * @param roleId
     * @param userIdList
     */
    public void changeRoleUsers(Integer roleId, List<Integer> userIdList) {
        // 获取该角色原始的用户ID列表
        List<Integer> originUserIdList = sysRoleUserMapper.getUserIdListByRoleIdList(Lists.newArrayList(roleId));

        // 判断两个集合是否相等，即当用户什么都没有改变时，无需执行保存操作，直接返回
        if (originUserIdList.size() == userIdList.size()) {
            Set<Integer> originUserIdSet = Sets.newHashSet(originUserIdList);
            Set<Integer> userIdSet = Sets.newHashSet(userIdList);
            originUserIdSet.removeAll(userIdSet);
            if (CollectionUtils.isEmpty(originUserIdSet)) {
                return;
            }
        }

        // 更新操作
        updateRoleUsers(roleId, userIdList);

        sysLogService.saveRoleUserLog(roleId, originUserIdList, userIdList);
    }

    /**
     * 更新角色用户信息
     *
     * @param roleId
     * @param userIdList
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateRoleUsers(int roleId, List<Integer> userIdList) {
        // 先删除之前的角色权限数据
        sysRoleUserMapper.deleteByRoleId(roleId);

        // 判断是否为空
        if (CollectionUtils.isEmpty(userIdList)) {
            return;
        }

        // 批量插入角色-用户数据
        List<SysRoleUser> roleUserList = Lists.newArrayList();
        for (Integer userId : userIdList) {
            SysRoleUser roleUser = SysRoleUser.builder().roleId(roleId).userId(userId).operateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest())).
                    operator(RequestHolder.getCurrentUser().getUsername()).operateTime(new Date()).build();
            roleUserList.add(roleUser);
        }
        sysRoleUserMapper.batchInsert(roleUserList);
    }

}
