package com.empress.controller;

import com.empress.common.JsonData;
import com.empress.param.RoleParam;
import com.empress.pojo.SysUser;
import com.empress.service.*;
import com.empress.util.StringUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 系统角色 Controller
 *
 * @author Hystar
 * @date 2018/10/9
 */
@Controller
@RequestMapping(value = "/sys/role")
public class SysRoleController {

    @Resource
    private SysRoleService sysRoleService;

    @Resource
    private SysTreeService sysTreeService;

    @Resource
    private SysRoleAclService sysRoleAclService;

    @Resource
    private SysRoleUserService sysRoleUserService;

    @Resource
    private SysUserService sysUserService;

    /**
     * 进入页面
     *
     * @return
     */
    @RequestMapping(value = "/role.page")
    public ModelAndView page() {
        return new ModelAndView("role");
    }

    /**
     * 新增角色信息
     *
     * @param roleParam
     * @return
     */
    @RequestMapping(value = "/save.json")
    @ResponseBody
    public JsonData saveRole(RoleParam roleParam) {
        sysRoleService.save(roleParam);
        return JsonData.success();
    }

    /**
     * 修改角色信息
     *
     * @param roleParam
     * @return
     */
    @RequestMapping(value = "/update.json")
    @ResponseBody
    public JsonData updateRole(RoleParam roleParam) {
        sysRoleService.update(roleParam);
        return JsonData.success();
    }

    /**
     * 获取角色列表
     *
     * @return
     */
    @RequestMapping(value = "/list.json")
    @ResponseBody
    public JsonData list() {
        return JsonData.success(sysRoleService.getAll());
    }

    /**
     * 获取当前角色所对应的权限树
     *
     * @param roleId
     * @return
     */
    @RequestMapping(value = "/roleTree.json")
    @ResponseBody
    public JsonData roleTree(@RequestParam(value = "roleId") Integer roleId) {
        return JsonData.success(sysTreeService.roleTree(roleId));
    }

    /**
     * 修改角色权限信息
     *
     * @param aclIds
     * @param roleId
     */
    @RequestMapping(value = "/changeAcls.json")
    @ResponseBody
    public JsonData changeRoleAcls(@RequestParam(value = "aclIds", required = false, defaultValue = "") String aclIds, @RequestParam(value = "roleId") Integer roleId) {
        List<Integer> aclIdList = StringUtil.splitToListInt(aclIds);
        sysRoleAclService.changeRoleAcls(roleId, aclIdList);
        return JsonData.success();
    }

    /**
     * 获取当前角色已选中的用户和未选中的用户
     *
     * @param roleId
     * @return
     */
    @RequestMapping(value = "/users.json")
    @ResponseBody
    public JsonData users(@RequestParam(value = "roleId") Integer roleId) {
        // 获取当前角色所对应的用户列表
        List<SysUser> selectedUserList = sysRoleUserService.getListByRoleId(roleId);

        // 获取所有用户列表
        List<SysUser> allUserList = sysUserService.getAll();

        // 获取未被选中的用户列表
        List<SysUser> unselectedUserList = Lists.newArrayList();
        Set<Integer> selectedUserIdSet = selectedUserList.stream().map(sysUser -> sysUser.getId()).collect(Collectors.toSet());
        for (SysUser user : allUserList) {
            if (user.getStatus() == 1 && !selectedUserIdSet.contains(user.getId())) {
                unselectedUserList.add(user);
            }
        }

        // 移除状态不为1的用户
//        selectedUserList = selectedUserList.stream().filter(sysUser -> sysUser.getStatus() != 1).collect(Collectors.toList());

        // 返回列表
        Map<String, List<SysUser>> map = Maps.newHashMap();
        map.put("selected", selectedUserList);
        map.put("unselected", unselectedUserList);
        return JsonData.success(map);
    }

    /**
     * 修改角色用户信息
     *
     * @param userIds
     * @param roleId
     */
    @RequestMapping(value = "/changeUsers.json")
    @ResponseBody
    public JsonData changeRoleUsers(@RequestParam(value = "userIds", required = false, defaultValue = "") String userIds, @RequestParam(value = "roleId") Integer roleId) {
        List<Integer> userIdList = StringUtil.splitToListInt(userIds);
        sysRoleUserService.changeRoleUsers(roleId, userIdList);
        return JsonData.success();
    }
}
