package com.empress.controller;

import com.empress.beans.PageQuery;
import com.empress.beans.PageResult;
import com.empress.common.JsonData;
import com.empress.param.AclParam;
import com.empress.pojo.SysAcl;
import com.empress.pojo.SysUser;
import com.empress.service.SysAclService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * 权限 Controller
 *
 * @author Hystar
 * @date 2018/10/9
 */
@Controller
@RequestMapping(value = "/sys/acl")
public class SysAclController {

    @Resource
    private SysAclService sysAclService;

    /**
     * 新增权限
     *
     * @param aclParam
     * @return
     */
    @RequestMapping(value = "/save.json")
    @ResponseBody
    public JsonData saveAcl(AclParam aclParam) {
        sysAclService.save(aclParam);
        return JsonData.success();
    }

    /**
     * 更新权限
     *
     * @param aclParam
     * @return
     */
    @RequestMapping(value = "/update.json")
    @ResponseBody
    public JsonData updateAcl(AclParam aclParam) {
        sysAclService.update(aclParam);
        return JsonData.success();
    }

    /**
     * 获取指定权限模块下的权限分页信息列表
     *
     * @param aclModuleId
     * @param pageQuery
     * @return
     */
    @RequestMapping(value = "/page.json")
    @ResponseBody
    public JsonData page(@RequestParam(value = "aclModuleId") int aclModuleId, PageQuery pageQuery) {
        PageResult<SysAcl> result = sysAclService.getPageByAclModuleId(aclModuleId, pageQuery);
        return JsonData.success(result);
    }
}
