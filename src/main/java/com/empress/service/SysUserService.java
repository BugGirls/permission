package com.empress.service;

import com.empress.beans.PageQuery;
import com.empress.beans.PageResult;
import com.empress.common.RequestHolder;
import com.empress.dao.SysRoleUserMapper;
import com.empress.dao.SysUserMapper;
import com.empress.exception.ParamException;
import com.empress.param.UserParam;
import com.empress.pojo.SysRole;
import com.empress.pojo.SysUser;
import com.empress.util.BeanValidator;
import com.empress.util.IpUtil;
import com.empress.util.MD5Util;
import com.empress.util.PasswordUtil;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Hystar
 * @date 2018/9/30
 */
@Service
public class SysUserService {

    @Resource
    private SysUserMapper sysUserMapper;

    @Resource
    private SysRoleUserMapper sysRoleUserMapper;

    @Resource
    private SysLogService sysLogService;

    /**
     * 新增一个用户
     * 手机号和邮箱为登录名，不能重复，姓名可以重复
     * 用户不填写密码，由系统生成，并通过邮件的形式将生成的密码发送给用户
     *
     * @param userParam
     */
    public void save(UserParam userParam) {
        // 验证传入的用户参数
        BeanValidator.check(userParam);

        // 检查手机号和邮箱是否存在，传入ID是为了在更新的时候也可以用此方法进行校验
        if (checkTelephoneExist(userParam.getTelephone(), userParam.getId())) {
            throw new ParamException("手机号已存在");
        }
        if (checkEmailExist(userParam.getMail(), userParam.getId())) {
            throw new ParamException("邮箱已存在");
        }

        // 密码由系统生成，并发送到所填写的邮箱地址
        // TODO: 2018/10/8  使用系统生成的密码
        String password = PasswordUtil.randomPassword();
        password = "123456";
        String encryptedPassword = MD5Util.encrypt(password);
        SysUser sysUser = SysUser.builder().username(userParam.getUsername()).telephone(userParam.getTelephone()).mail(userParam.getMail())
                .password(encryptedPassword).deptId(userParam.getDeptId()).status(userParam.getStatus()).remark(userParam.getRemark()).build();
        sysUser.setOperator(RequestHolder.getCurrentUser().getUsername());
        sysUser.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        sysUser.setOperateTime(new Date());

        // todo 发送邮箱

        // 当邮件发送成功后，持久化到数据库
        sysUserMapper.insertSelective(sysUser);

        sysLogService.saveUserLog(null, sysUser);
    }

    /**
     * 更新用户信息
     *
     * @param userParam
     */
    public void update(UserParam userParam) {
        // 验证传入的用户参数
        BeanValidator.check(userParam);

        // 检查手机号和邮箱是否存在，传入ID是为了在更新的时候也可以用此方法进行校验
        if (checkTelephoneExist(userParam.getTelephone(), userParam.getId())) {
            throw new ParamException("手机号已存在");
        }
        if (checkEmailExist(userParam.getMail(), userParam.getId())) {
            throw new ParamException("邮箱已存在");
        }

        SysUser before = sysUserMapper.selectByPrimaryKey(userParam.getId());
        Preconditions.checkNotNull(before, "待更新的用户不存在");

        SysUser after = SysUser.builder().id(userParam.getId()).username(userParam.getUsername()).telephone(userParam.getTelephone()).mail(userParam.getMail())
                .deptId(userParam.getDeptId()).status(userParam.getStatus()).remark(userParam.getRemark()).build();
        after.setOperator(RequestHolder.getCurrentUser().getUsername());
        after.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        after.setOperateTime(new Date());

        sysUserMapper.updateByPrimaryKeySelective(after);

        sysLogService.saveUserLog(before, after);
    }

    /**
     * 获取指定部门下用户分页信息列表
     *
     * @param deptId
     * @param pageQuery
     * @return
     */
    public PageResult<SysUser> getPageByDeptId(int deptId, PageQuery pageQuery) {
        BeanValidator.check(pageQuery);

        int count = sysUserMapper.countByDeptId(deptId);
        if (count > 0) {
            List<SysUser> sysUserList = sysUserMapper.getPageByDeptId(deptId, pageQuery);
            return PageResult.<SysUser>builder().total(count).data(sysUserList).build();
        }
        return PageResult.<SysUser>builder().build();
    }

    /**
     * 验证邮箱是否存在
     *
     * @param mail
     * @param userId
     * @return
     */
    public boolean checkEmailExist(String mail, Integer userId) {
        return sysUserMapper.countByMail(mail, userId) > 0;
    }

    /**
     * 验证手机号是否存在
     *
     * @param telephone
     * @param userId
     * @return
     */
    public boolean checkTelephoneExist(String telephone, Integer userId) {
        return sysUserMapper.countByMail(telephone, userId) > 0;
    }

    /**
     * 通过关键字获取用户信息，关键字为手机号或邮箱
     *
     * @param keyword
     * @return
     */
    public SysUser findByKeyword(String keyword) {
        return sysUserMapper.findByKeyword(keyword);
    }

    /**
     * 获取所有用户列表
     *
     * @return
     */
    public List<SysUser> getAll() {
        return sysUserMapper.getAll();
    }

    /**
     * 通过角色列表获取用户列表
     *
     * @param sysRoles
     * @return
     */
    public List<SysUser> getUserListByRoleList(List<SysRole> sysRoles) {
        if (CollectionUtils.isEmpty(sysRoles)) {
            return Lists.newArrayList();
        }
        List<Integer> roleIdList = sysRoles.stream().map(sysRole -> sysRole.getId()).collect(Collectors.toList());
        List<Integer> userIdList = sysRoleUserMapper.getUserIdListByRoleIdList(roleIdList);
        if(CollectionUtils.isEmpty(userIdList)) {
            return Lists.newArrayList();
        }
        List<SysUser> sysUserList = sysUserMapper.getByIdList(userIdList);
        return sysUserList;
    }

}
