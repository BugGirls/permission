package com.empress.service;

import com.empress.common.RequestHolder;
import com.empress.dao.SysAclModuleMapper;
import com.empress.exception.ParamException;
import com.empress.param.AclModuleParam;
import com.empress.pojo.SysAclModule;
import com.empress.util.BeanValidator;
import com.empress.util.IpUtil;
import com.empress.util.LevelUtil;
import com.google.common.base.Preconditions;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 权限模块 Service
 *
 * @author Hystar
 * @date 2018/10/9
 */
@Service
public class SysAclModuleService {

    @Resource
    private SysAclModuleMapper sysAclModuleMapper;

    /**
     * 新增权限模块
     *
     * @param param
     */
    public void save(AclModuleParam param) {
        // 参数校验
        BeanValidator.check(param);

        // 判断同一层级下是否存在相同的权限模块
        if (checkExist(param.getParentId(), param.getName(), param.getId())) {
            throw new ParamException("同一层级下存在相同名字的权限模块");
        }

        // 新增权限模块
        SysAclModule sysAclModule = SysAclModule.builder().name(param.getName()).parentId(param.getParentId()).status(param.getStatus()).seq(param.getSeq()).remark(param.getRemark()).build();
        // 计算level
        sysAclModule.setLevel(LevelUtil.calculateLevel(getLevel(sysAclModule.getParentId()), sysAclModule.getParentId()));
        sysAclModule.setOperator(RequestHolder.getCurrentUser().getUsername());
        sysAclModule.setOperateIp(IpUtil.getUserIP(RequestHolder.getCurrentRequest()));
        sysAclModule.setOperateTime(new Date());

        sysAclModuleMapper.insertSelective(sysAclModule);
    }

    /**
     * 更新权限模块，需要更新当前权限模块和当前权限模块下的子模块
     *
     * @param param
     */
    public void update(AclModuleParam param) {
        // 参数校验
        BeanValidator.check(param);

        // 判断同一层级下是否存在相同的权限模块
        if (checkExist(param.getParentId(), param.getName(), param.getId())) {
            throw new ParamException("同一层级下存在相同名字的权限模块");
        }

        // 判断待更新的权限模块
        SysAclModule before = sysAclModuleMapper.selectByPrimaryKey(param.getId());
        Preconditions.checkNotNull(before, "待更新的权限模块不存在");

        // 更新后的权限模块
        SysAclModule after = SysAclModule.builder().id(param.getId()).name(param.getName()).parentId(param.getParentId()).status(param.getStatus()).seq(param.getSeq()).remark(param.getRemark()).build();
        after.setLevel(LevelUtil.calculateLevel(getLevel(param.getParentId()), param.getParentId()));
        after.setOperator(RequestHolder.getCurrentUser().getUsername());
        after.setOperateIp(IpUtil.getUserIP(RequestHolder.getCurrentRequest()));
        after.setOperateTime(new Date());

        // 更新子模块层级信息
        updateWithChild(before, after);
    }

    /**
     * 更新子模块层级信息
     *
     * @param before
     * @param after
     */
    @Transactional(rollbackFor = Exception.class)
    private void updateWithChild(SysAclModule before, SysAclModule after) {
        String newLevelPrefix = after.getLevel();
        String oldLevelPrefix = before.getLevel();
        // 如果当前层级与之前的层级不一致，说明修改了权限模块的层级，则需要做子模块层级的更新
        if (!after.getLevel().equals(before.getLevel())) {
            // 获取当前权限模块的子模块
            List<SysAclModule> sysAclModuleList = sysAclModuleMapper.getChildAclModuleListByLevel(before.getLevel() + LevelUtil.SEPARATOR + before.getId());
            if (CollectionUtils.isNotEmpty(sysAclModuleList)) {
                for (SysAclModule sysAclModule : sysAclModuleList) {
                    String level = sysAclModule.getLevel();
                    if (level.indexOf(oldLevelPrefix) == 0) {
                        level = newLevelPrefix + level.substring(oldLevelPrefix.length());
                        sysAclModule.setLevel(level);
                    }
                }
                sysAclModuleMapper.batchUpdateLevel(sysAclModuleList);
            }
        }

        sysAclModuleMapper.updateByPrimaryKeySelective(after);
    }

    /**
     * 判断同级权限模块名称是否重复
     *
     * @param parentId
     * @param aclModuleName
     * @param aclModuleId
     * @return
     */
    private boolean checkExist(Integer parentId, String aclModuleName, Integer aclModuleId) {
        return sysAclModuleMapper.countByNameAndParentId(parentId, aclModuleName, aclModuleId) > 0;
    }

    /**
     * 获取当前权限模块的层级
     *
     * @param aclModuleId
     * @return
     */
    private String getLevel(Integer aclModuleId) {
        SysAclModule aclModule = sysAclModuleMapper.selectByPrimaryKey(aclModuleId);
        if (aclModule == null) {
            return null;
        }
        return aclModule.getLevel();
    }
}
