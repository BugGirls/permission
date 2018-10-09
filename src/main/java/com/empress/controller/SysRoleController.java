package com.empress.controller;

import com.empress.service.SysRoleService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

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


}
