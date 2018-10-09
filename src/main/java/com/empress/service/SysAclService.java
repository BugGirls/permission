package com.empress.service;

import com.empress.beans.PageQuery;
import com.empress.beans.PageResult;
import com.empress.common.RequestHolder;
import com.empress.dao.SysAclMapper;
import com.empress.exception.ParamException;
import com.empress.param.AclParam;
import com.empress.pojo.SysAcl;
import com.empress.util.BeanValidator;
import com.empress.util.IpUtil;
import com.google.common.base.Preconditions;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author Hystar
 * @date 2018/10/9
 */
@Service
public class SysAclService {

    @Resource
    private SysAclMapper sysAclMapper;

    /**
     * 新增权限信息
     *
     * @param param
     */
    public void save(AclParam param) {
        // 参数校验
        BeanValidator.check(param);

        // 判断同一层级下是否存在相同的权限模块
        if (checkExist(param.getAclModuleId(), param.getName(), param.getId())) {
            throw new ParamException("当前权限模块下存在相同名称的权限");
        }

        // 新增权限
        SysAcl sysAcl = SysAcl.builder().name(param.getName()).aclModuleId(param.getAclModuleId()).url(param.getUrl())
                .type(param.getType()).status(param.getStatus()).seq(param.getSeq()).remark(param.getRemark()).build();
        // 计算code
        sysAcl.setCode(generateCode());
        sysAcl.setOperator(RequestHolder.getCurrentUser().getUsername());
        sysAcl.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        sysAcl.setOperateTime(new Date());

        sysAclMapper.insertSelective(sysAcl);
    }

    /**
     * 修改权限信息
     *
     * @param param
     */
    public void update(AclParam param) {
        // 参数校验
        BeanValidator.check(param);

        // 判断同一层级下是否存在相同的权限模块
        if (checkExist(param.getAclModuleId(), param.getName(), param.getId())) {
            throw new ParamException("当前权限模块下存在相同名称的权限");
        }

        // 判断待更新的权限模块
        SysAcl before = sysAclMapper.selectByPrimaryKey(param.getId());
        Preconditions.checkNotNull(before, "待更新的权限不存在");

        // 更新后的权限模块
        SysAcl after = SysAcl.builder().id(param.getId()).name(param.getName()).aclModuleId(param.getAclModuleId()).url(param.getUrl())
                .type(param.getType()).status(param.getStatus()).seq(param.getSeq()).remark(param.getRemark()).build();
        after.setOperator(RequestHolder.getCurrentUser().getUsername());
        after.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        after.setOperateTime(new Date());

        sysAclMapper.updateByPrimaryKeySelective(after);
    }

    /**
     * 判断同级权限模块名称是否重复
     *
     * @param aclModuleId
     * @param name
     * @param id
     * @return
     */
    private boolean checkExist(Integer aclModuleId, String name, Integer id) {
        return sysAclMapper.countByNameAndAclModuleId(aclModuleId, name, id) > 0;
    }

    /**
     * 生成权限code值
     *
     * @return
     */
    private String generateCode() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        return dateFormat.format(new Date()) + "_" + (int) (Math.random() * 100);
    }

    /**
     * 获取指定权限模块下的权限分页信息列表
     *
     * @param aclModuleId
     * @param pageQuery
     * @return
     */
    public PageResult<SysAcl> getPageByAclModuleId(int aclModuleId, PageQuery pageQuery) {
        BeanValidator.check(pageQuery);

        int count = sysAclMapper.countByAclModuleId(aclModuleId);
        if (count > 0) {
            List<SysAcl> aclList = sysAclMapper.getPageByAclModuleId(aclModuleId, pageQuery);
            return PageResult.<SysAcl>builder().data(aclList).total(count).build();
        }

        return PageResult.<SysAcl>builder().build();
    }
}
