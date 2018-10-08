package com.empress.service;

import com.empress.dao.SysUserMapper;
import com.empress.exception.ParamException;
import com.empress.param.UserParam;
import com.empress.pojo.SysUser;
import com.empress.util.BeanValidator;
import com.empress.util.MD5Util;
import com.empress.util.PasswordUtil;
import com.google.common.base.Preconditions;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author Hystar
 * @date 2018/9/30 0030
 */
@Service
public class SysUserService {

    @Resource
    private SysUserMapper sysUserMapper;

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

        // 检查手机号和邮箱是否存在
        if (checkTelephoneExist(userParam.getTelephone(), userParam.getId())) {
            throw new ParamException("手机号已存在");
        }
        if (checkEmailExist(userParam.getMail(), userParam.getId())) {
            throw new ParamException("邮箱已存在");
        }

        // 密码由系统生成，并发送到所填写的邮箱地址
        String password = PasswordUtil.randomPassword();
        password = "123456";
        String encryptedPassword = MD5Util.encrypt(password);
        SysUser sysUser = SysUser.builder().username(userParam.getUsername()).telephone(userParam.getTelephone()).mail(userParam.getMail())
                .password(encryptedPassword).deptId(userParam.getDeptId()).status(userParam.getStatus()).remark(userParam.getRemark()).build();
        sysUser.setOperator("system");
        sysUser.setOperateIp("127.0.0.1");
        sysUser.setOperateTime(new Date());

        // todo 发送邮箱

        // 当邮件发送成功后，持久化到数据库
        sysUserMapper.insertSelective(sysUser);
    }

    /**
     * 更新用户信息
     *
     * @param userParam
     */
    public void update(UserParam userParam) {
        // 验证传入的用户参数
        BeanValidator.check(userParam);

        // 检查手机号和邮箱是否存在
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
        sysUserMapper.updateByPrimaryKeySelective(after);
    }

    /**
     * 验证邮箱是否存在
     *
     * @param mail
     * @param userId
     * @return
     */
    public boolean checkEmailExist(String mail, Integer userId) {
        return false;
    }

    /**
     * 验证手机号是否存在
     *
     * @param telephone
     * @param userId
     * @return
     */
    public boolean checkTelephoneExist(String telephone, Integer userId) {
        return false;
    }

    public SysUser findByKeyword(String keyword) {
        return null;
    }
}
