package com.empress.filter;

import com.empress.common.ApplicationContentHelper;
import com.empress.common.JsonData;
import com.empress.common.RequestHolder;
import com.empress.pojo.SysUser;
import com.empress.service.SysCoreService;
import com.empress.util.JsonMapper;
import com.google.common.base.Splitter;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 权限拦截
 *
 * @author Hystar
 * @date 2018/10/12 0012
 */
@Slf4j
public class AclControlFilter implements Filter {

    /**
     * 定义要排除URl的Set集合
     */
    private static Set<String> exclusionUrlSet = Sets.newConcurrentHashSet();

    /**
     * 定义一个无权限访问的页面
     */
    private final static String noAuthUrl = "/sys/user/noAuth.page";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // 获取要排除的URL
        String exclusionUrls = filterConfig.getInitParameter("exclusionUrls");
        // 通过“,”进行分割 转换成list
        List<String> exclusionUrlList = Splitter.on(",").trimResults().omitEmptyStrings().splitToList(exclusionUrls);

        exclusionUrlSet = Sets.newConcurrentHashSet(exclusionUrlList);
        exclusionUrlSet.add(noAuthUrl);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        // 获取参数集合
        Map requestMap = request.getParameterMap();

        // 获取请求路径
        String servletPath = request.getServletPath();
        // 如果请求的路径为要排除的路径，则放行
        if (exclusionUrlSet.contains(servletPath)) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        // 获取当前登录用户
        SysUser sysUser = RequestHolder.getCurrentUser();
        if (sysUser == null) {
            log.info("其他人访问了路径：{}, 但是没有登录, 访问的参数：{}", servletPath, JsonMapper.objToString(requestMap));
            // 如果用户没有登录，则返回没有授权
            noAuth(request, response);
            return;
        }

        // 验证是否有权限访问URL
        SysCoreService sysCoreService = ApplicationContentHelper.popBean(SysCoreService.class);
        if (!sysCoreService.hasUrlAcl(servletPath)) {
            log.info("{} 访问了路径：{}, 但是没有权限, 访问的参数：{}", sysUser.getUsername(), servletPath, JsonMapper.objToString(requestMap));
            // 如果当前登录用户没有权限访问当前请求的路径，返回没有授权
            noAuth(request, response);
            return;
        }

        // 放行
        filterChain.doFilter(servletRequest, servletResponse);
        return;
    }

    /**
     * 处理一个请求没有授权时的方法
     *
     * @param request
     * @param response
     */
    private void noAuth(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String servletPath = request.getServletPath();
        // 判断当前请求是json请求还是页面请求
        if (servletPath.endsWith(".json")) {
            JsonData jsonData = JsonData.fail("没有访问权限，如需要访问，请联系管理员");
            response.setHeader("Content-Type", "application/json");
            response.getWriter().print(JsonMapper.objToString(jsonData));
            return;
        } else {
            // 页面跳转
            clientRedirect(noAuthUrl, response);
            return;
        }
    }

    private void clientRedirect(String url, HttpServletResponse response) throws IOException{
        response.setHeader("Content-Type", "text/html");
        response.getWriter().print("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">\n"
                + "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" + "<head>\n" + "<meta http-equiv=\"content-type\" content=\"text/html; charset=UTF-8\"/>\n"
                + "<title>跳转中...</title>\n" + "</head>\n" + "<body>\n" + "跳转中，请稍候...\n" + "<script type=\"text/javascript\">//<![CDATA[\n"
                + "window.location.href='" + url + "?ret='+encodeURIComponent(window.location.href);\n" + "//]]></script>\n" + "</body>\n" + "</html>\n");
    }

    @Override
    public void destroy() {

    }
}
