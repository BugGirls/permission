package com.empress.common;

import com.empress.exception.ParamException;
import com.empress.exception.PermissionException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 全局异常处理
 *
 * @author Hystar
 * @date 2018/7/3
 */
@Component
@Slf4j
public class SpringExceptionResolver implements HandlerExceptionResolver {

    /**
     * 请求的是json数据还是page页面
     */
    private static final String SUFFIX_JSON = ".json";
    private static final String SUFFIX_PAGE = ".page";

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        String url = request.getRequestURL().toString();
        ModelAndView modelAndView;
        String defaultMsg = "System Error";

        // 要求所有请求json数据的都以.json结尾, 所有请求page页面的都以.page结尾
        if (url.endsWith(SUFFIX_JSON)) {
            if(ex instanceof PermissionException || ex instanceof ParamException) {
                JsonData result = JsonData.fail(ex.getMessage());
                modelAndView = new ModelAndView("jsonView", result.toMap());
            } else {
                log.error("unknown json exception, url:" + url, ex);
                JsonData result = JsonData.fail(defaultMsg);
                modelAndView = new ModelAndView("jsonView", result.toMap());
            }
        } else if (url.endsWith(SUFFIX_PAGE)) {
            log.error("unknown page exception, url:" + url, ex);
            JsonData result = JsonData.fail(defaultMsg);
            modelAndView = new ModelAndView("exception", result.toMap());
        } else {
            log.error("unknown exception, url:" + url, ex);
            JsonData result = JsonData.fail(defaultMsg);
            modelAndView = new ModelAndView("jsonView", result.toMap());
        }

        return modelAndView;
    }
}
