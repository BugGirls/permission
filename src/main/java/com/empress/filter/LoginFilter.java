package com.empress.filter;

import com.empress.common.RequestHolder;
import com.empress.pojo.SysUser;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录过滤器，判断用户是否登录
 *
 * @author Hystar
 * @date 2018/10/8
 */
@Slf4j
public class LoginFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        // 获取session中的用户登录信息
        SysUser sysUser = (SysUser) request.getSession().getAttribute("user");
        if (sysUser == null) {
            // 用户没有登录，重定向到登录页面
            String path = "/login.jsp";
            response.sendRedirect(path);
            return;
        }

        // 用户已经登录，将登录信息添加到ThreadLocal中
        RequestHolder.add(sysUser);
        RequestHolder.add(request);

        // 放行
        filterChain.doFilter(servletRequest, servletResponse);
        return;
    }

    @Override
    public void destroy() {

    }
}
