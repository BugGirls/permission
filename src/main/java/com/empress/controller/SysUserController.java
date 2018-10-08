package com.empress.controller;

import com.empress.common.JsonData;
import com.empress.param.UserParam;
import com.empress.service.SysUserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

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
}
