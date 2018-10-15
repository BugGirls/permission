package com.empress.controller;

import com.empress.beans.LogType;
import com.empress.common.RequestHolder;
import com.empress.dao.SysLogMapper;
import com.empress.pojo.*;
import com.empress.service.SysLogService;
import com.empress.util.IpUtil;
import com.empress.util.JsonMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author Hystar
 * @date 2018/10/12 0012
 */
@RequestMapping(value = "/sys/log")
@Controller
public class SysLogController {

    @Resource
    private SysLogService sysLogService;

}
