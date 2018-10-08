package com.empress.controller;

import com.empress.pojo.SysUser;
import com.empress.service.SysUserService;
import com.empress.util.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 前台用户操作管理
 *
 * @author Hystar
 * @date 2018/9/30
 */
@Controller
public class UserController {

    @Resource
    private SysUserService sysUserService;

    /**
     * 用户登录
     *
     * 重定向，response.sendRedirect()，重定向之后的代码会继续执行
     * 转发,request.getRequestDispatcher().forward(),与重定向一样,转发之后的代码也会执行,所有代码执行完毕才跳转
     *
     * @param request
     * @param response
     * @throws IOException
     * @throws ServletException
     */
    @RequestMapping(value = "/login.page")
    public void login(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        // 登录之后要跳转的页面
        String ret = request.getParameter("ret");

        // 获取用户信息
        SysUser sysUser = sysUserService.findByKeyword(username);
        String errorMsg = "";

        if (StringUtils.isBlank(username)) {
            errorMsg = "用户名不可以为空";
        } else if (StringUtils.isBlank(password)) {
            errorMsg = "密码不可以为空";
        } else if (sysUser == null) {
            errorMsg = "查询不到指定的用户";
        } else if (!sysUser.getPassword().equals(MD5Util.encrypt(password))) {
            errorMsg = "用户名或密码错误";
        } else if (sysUser.getStatus() != 1) {
            errorMsg = "用户已被冻结，请联系管理员";
        } else {
            // 登录成功
            request.getSession().setAttribute("user", sysUser);
            if (StringUtils.isNotBlank(ret)) {
                response.sendRedirect(ret);
            } else {
                response.sendRedirect("/admin/index.page");
            }
        }

        // 登录失败
        if (StringUtils.isNotBlank(errorMsg)) {
            request.setAttribute("error", errorMsg);
            request.setAttribute("username", username);
            if (StringUtils.isNotBlank(ret)) {
                request.setAttribute("ret", ret);
            }
            String path = "login.jsp";
            request.getRequestDispatcher(path).forward(request, response);
        }
    }

    /**
     * 退出登录
     *
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "/logout.page")
    public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.getSession().invalidate();
        String path = "login.jsp";
        response.sendRedirect(path);
    }
}
