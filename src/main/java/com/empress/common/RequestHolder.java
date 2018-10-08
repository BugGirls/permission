package com.empress.common;

import com.empress.pojo.SysUser;

import javax.servlet.http.HttpServletRequest;

/**
 * 将request 中的数据放入到ThreadLocal 中，这样可以避免高并发请求下的数据访问问题
 *
 * @author Hystar
 * @date 2018/10/8
 */
public class RequestHolder {

    private static final ThreadLocal<SysUser> userHolder = new ThreadLocal<>();

    private static final ThreadLocal<HttpServletRequest> requestHolder = new ThreadLocal<>();

    public static void add(SysUser sysUser) {
        userHolder.set(sysUser);
    }

    public static void add(HttpServletRequest request) {
        requestHolder.set(request);
    }

    public static SysUser getCurrentUser() {
        return userHolder.get();
    }

    public static HttpServletRequest getCurrentRequest() {
        return requestHolder.get();
    }

    public static void remove() {
        userHolder.remove();
        requestHolder.remove();
    }
}
