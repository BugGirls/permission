package com.empress.controller;

import com.empress.beans.PageQuery;
import com.empress.beans.PageResult;
import com.empress.common.JsonData;
import com.empress.param.UserParam;
import com.empress.pojo.SysUser;
import com.empress.service.SysRoleService;
import com.empress.service.SysTreeService;
import com.empress.service.SysUserService;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 系统用户管理
 *
 * @author Hystar
 * @date 2018/9/30
 */
@Controller
@RequestMapping(value = "/sys/user")
public class SysUserController {

    @Resource
    private SysUserService sysUserService;

    @Resource
    private SysTreeService sysTreeService;

    @Resource
    private SysRoleService sysRoleService;

    /**
     * 新增用户
     *
     * @param param
     * @return
     */
    @RequestMapping(value = "/save.json")
    @ResponseBody
    public JsonData saveUser(UserParam param) {
        sysUserService.save(param);
        return JsonData.success();
    }

    /**
     * 更新用户
     *
     * @param param
     * @return
     */
    @RequestMapping(value = "/update.json")
    @ResponseBody
    public JsonData updateUser(UserParam param) {
        sysUserService.update(param);
        return JsonData.success();
    }

    /**
     * 获取指定部门下用户分页信息列表
     *
     * @param deptId
     * @param pageQuery
     * @return
     */
    @RequestMapping(value = "/page.json")
    @ResponseBody
    public JsonData page(@RequestParam(value = "deptId") int deptId, PageQuery pageQuery) {
        PageResult<SysUser> result = sysUserService.getPageByDeptId(deptId, pageQuery);
        return JsonData.success(result);
    }

    /**
     * 获取当前用户所对应的权限和角色
     *
     * @param userId
     * @return
     */
    @RequestMapping(value = "/acls.json")
    @ResponseBody
    public JsonData acls(@RequestParam(value = "userId") Integer userId) {
        Map<String, Object> map = Maps.newHashMap();
        map.put("acls", sysTreeService.userAclTree(userId));
        map.put("roles", sysRoleService.getRoleListByUserId(userId));
        return JsonData.success(map);
    }
}
