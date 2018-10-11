package com.empress.controller;

import com.empress.common.JsonData;
import com.empress.dto.AclModuleLevelDTO;
import com.empress.param.AclModuleParam;
import com.empress.service.SysAclModuleService;
import com.empress.service.SysTreeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

/**
 * 权限模块 Controller
 *
 * @author Hystar
 * @date 2018/10/9
 */
@Controller
@RequestMapping(value = "/sys/aclModule")
@Slf4j
public class SysAclModuleController {

    @Resource
    private SysAclModuleService sysAclModuleService;

    @Resource
    private SysTreeService sysTreeService;

    /**
     * 进入权限模块页面
     *
     * @return
     */
    @RequestMapping(value = "/acl.page")
    public ModelAndView page() {
        return new ModelAndView("acl");
    }

    /**
     * 获取权限模块树
     *
     * @return
     */
    @RequestMapping(value = "/tree.json")
    @ResponseBody
    public JsonData tree() {
        List<AclModuleLevelDTO> deptLevelDTOList = sysTreeService.aclModuleTree();
        return JsonData.success(deptLevelDTOList);
    }

    /**
     * 新增权限模块
     *
     * @param aclModuleParam
     * @return
     */
    @RequestMapping(value = "/save.json")
    @ResponseBody
    public JsonData saveAclModule(AclModuleParam aclModuleParam) {
        sysAclModuleService.save(aclModuleParam);
        return JsonData.success();
    }

    /**
     * 更新权限模块
     *
     * @param aclModuleParam
     * @return
     */
    @RequestMapping(value = "/update.json")
    @ResponseBody
    public JsonData updateAclModule(AclModuleParam aclModuleParam) {
        sysAclModuleService.update(aclModuleParam);
        return JsonData.success();
    }

    /**
     * 删除权限模块
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete.json")
    @ResponseBody
    public JsonData deleteAclModule(@RequestParam(value = "id") Integer id) {
        sysAclModuleService.delete(id);
        return JsonData.success();
    }
}
