package com.empress.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 首页管理
 *
 * @author Hystar
 * @date 2018/9/30 0030
 */
@Controller
@RequestMapping(value = "/admin")
public class AdminController {

    /**
     * 跳转到首页
     *
     * @return
     */
    @RequestMapping(value = "index.page")
    public ModelAndView index() {
        return new ModelAndView("admin");
    }

}
