package com.empress.controller;

import com.empress.common.JsonData;
import com.empress.dto.DeptLevelDTO;
import com.empress.param.DeptParam;
import com.empress.service.SysDeptService;
import com.empress.service.SysTreeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

/**
 * 系统部门管理
 *
 * @author Hystar
 * @date 2018/9/6
 */
@Controller
@RequestMapping(value = "/sys/dept")
@Slf4j
public class SysDeptController {

    @Resource
    private SysDeptService sysDeptService;

    @Resource
    private SysTreeService sysTreeService;

    /**
     * 进入页面
     *
     * @return
     */
    @RequestMapping(value = "/dept.page")
    public ModelAndView page() {
        return new ModelAndView("dept");
    }

    /**
     * 新增部门
     *
     * @param deptParam
     * @return
     */
    @RequestMapping(value = "/save.json")
    @ResponseBody
    public JsonData saveDept(DeptParam deptParam) {
        sysDeptService.save(deptParam);
        return JsonData.success();
    }

    /**
     * 获取部门树
     *
     * @return
     */
    @RequestMapping(value = "/tree.json")
    @ResponseBody
    public JsonData tree() {
        List<DeptLevelDTO> deptLevelDTOList = sysTreeService.deptTree();
        return JsonData.success(deptLevelDTOList);
    }

    /**
     * 更新部门
     *
     * @param deptParam
     * @return
     */
    @RequestMapping(value = "/update.json")
    @ResponseBody
    public JsonData updateDept(DeptParam deptParam) {
        sysDeptService.update(deptParam);
        return JsonData.success();
    }
}
