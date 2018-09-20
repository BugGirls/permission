package com.empress.controller;

import com.empress.common.ApplicationContentHelper;
import com.empress.common.JsonData;
import com.empress.dao.SysAclModuleMapper;
import com.empress.param.TestVo;
import com.empress.pojo.SysAclModule;
import com.empress.util.BeanValidator;
import com.empress.util.JsonMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * @author Hystar
 * @date 2018/7/3
 */
@Controller
@RequestMapping(value = "/test")
@Slf4j
public class TestController {

    @RequestMapping(value = "/hello.json")
    public String hello() {
        log.info("hello");
//        throw new RuntimeException("test exception");
        return "jsonView";
    }

    @RequestMapping(value = "/validate.json")
    @ResponseBody
    public JsonData validate(TestVo vo) {
        log.info("validate");
        SysAclModuleMapper sysAclModuleMapper = ApplicationContentHelper.popBean(SysAclModuleMapper.class);
        SysAclModule sysAclModule = sysAclModuleMapper.selectByPrimaryKey(1);
        log.info(JsonMapper.objToString(sysAclModule));
        BeanValidator.check(vo);
        return JsonData.success("test validate");
    }
}
