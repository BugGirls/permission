package com.empress.service;

import com.empress.dao.SysDeptMapper;
import com.empress.exception.ParamException;
import com.empress.param.DeptParam;
import com.empress.pojo.SysDept;
import com.empress.util.BeanValidator;
import com.empress.util.LevelUtil;
import com.google.common.base.Preconditions;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author Hystar
 * @date 2018/9/6
 */
@Service
public class SysDeptService {

    @Resource
    private SysDeptMapper sysDeptMapper;

    /**
     * 新增一个部门
     *
     * @param deptParam
     */
    public void save(DeptParam deptParam) {
        // 验证传入的部门参数
        BeanValidator.check(deptParam);

        // 检查同级部门的名称是否重复
        if (checkExist(deptParam.getParentId(), deptParam.getName(), deptParam.getId())) {
            throw new ParamException("同一层级下存在相同名称的部门");
        }

        // 新增部门
        SysDept sysDept = SysDept.builder().name(deptParam.getName()).parentId(deptParam.getParentId()).seq(deptParam.getSeq()).remark(deptParam.getRemark()).build();
        // 计算level
        sysDept.setLevel(LevelUtil.calculateLevel(getLevel(deptParam.getParentId()), deptParam.getParentId()));
        sysDept.setOperator("system");
        sysDept.setOperateIp("127.0.0.1");
        sysDept.setOperateTime(new Date());
        sysDeptMapper.insertSelective(sysDept);
    }

    /**
     * 更新一个部门
     * 需要更新当前部门和当前部门下的子部门
     *
     * @param deptParam
     */
    public void update(DeptParam deptParam) {
        // 验证传入的部门参数
        BeanValidator.check(deptParam);

        // 检查同级部门的名称是否重复
        if (checkExist(deptParam.getParentId(), deptParam.getName(), deptParam.getId())) {
            throw new ParamException("同一层级下存在相同名称的部门");
        }

        // 判断待更新的部门
        SysDept before = sysDeptMapper.selectByPrimaryKey(deptParam.getId());
        Preconditions.checkNotNull(before, "待更新的部门不存在");

        // 更新后的部门
        SysDept after = SysDept.builder().id(deptParam.getId()).name(deptParam.getName()).parentId(deptParam.getParentId()).seq(deptParam.getSeq()).remark(deptParam.getRemark()).build();
        after.setLevel(LevelUtil.calculateLevel(getLevel(deptParam.getParentId()), deptParam.getParentId()));
        after.setOperator("system-update");
        after.setOperateIp("127.0.0.1");
        after.setOperateTime(new Date());

        updateWithChild(before, after);
    }

    /**
     * 更新子部门层级信息
     *
     * @param before
     * @param after
     */
    @Transactional(rollbackFor = Exception.class)
    private void updateWithChild(SysDept before, SysDept after) {
        String newLevelPrefix = after.getLevel();
        String oldLevelPrefix = before.getLevel();
        // 如果当前层级与之前的层级不一致，说明修改了部门层级，则需要做子部门层级的更新
        if (!after.getLevel().equals(before.getLevel())) {
            // 获取当前部门的子部门
            List<SysDept> deptList = sysDeptMapper.getChildDeptListByLevel(before.getLevel() + LevelUtil.SEPARATOR + before.getId());
            if (CollectionUtils.isNotEmpty(deptList)) {
                for (SysDept sysDept : deptList) {
                    String level = sysDept.getLevel();
                    if (level.indexOf(oldLevelPrefix) == 0) {
                        level = newLevelPrefix + level.substring(oldLevelPrefix.length());
                        sysDept.setLevel(level);
                    }
                }
                sysDeptMapper.batchUpdateLevel(deptList);
            }
        }

        sysDeptMapper.updateByPrimaryKey(after);
    }


    /**
     * 判断同级部门名称是否重复
     *
     * @param parentId
     * @param deptName
     * @param deptId
     * @return
     */
    private boolean checkExist(Integer parentId, String deptName, Integer deptId) {
        return sysDeptMapper.countByNameAndParentId(parentId, deptName, deptId) > 0;
    }

    /**
     * 获取当前部门的层级
     *
     * @param deptId
     * @return
     */
    private String getLevel(Integer deptId) {
        SysDept sysDept = sysDeptMapper.selectByPrimaryKey(deptId);
        if (sysDept == null) {
            return null;
        }
        return sysDept.getLevel();
    }
}
