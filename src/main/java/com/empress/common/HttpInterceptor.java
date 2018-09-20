package com.empress.common;

import com.empress.util.JsonMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Http请求前后监听工具
 *
 * @author Hystar
 * @date 2018/9/6
 */
@Slf4j
public class HttpInterceptor extends HandlerInterceptorAdapter {
    /**
     * This implementation always returns {@code true}.
     * 请求之前调用
     *
     * @param request
     * @param response
     * @param handler
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String url = request.getRequestURI().toString();
        Map paramMap = request.getParameterMap();
        log.info("请求开始，进入preHandle()方法");
        log.info("url:{}，param:{}", url, JsonMapper.objToString(paramMap));
        return true;
    }

    /**
     * This implementation is empty.
     * 请求正常结束之后调用
     *
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("请求结束，进入postHandle()方法");
    }

    /**
     * This implementation is empty.
     * 所有请求结束之后调用 - 正常或出现异常
     *
     * @param request
     * @param response
     * @param handler
     * @param ex
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        log.info("请求结束，进入afterCompletion()方法");
    }
}
