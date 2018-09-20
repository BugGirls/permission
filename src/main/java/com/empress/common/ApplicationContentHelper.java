package com.empress.common;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * 获取Spring上下文工具
 *
 * @author Hystar
 * @date 2018/7/5
 */
@Component
@Lazy(value = false)
public class ApplicationContentHelper implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        applicationContext = context;
    }

    public static <T> T popBean(Class<T> tClass) {
        if (applicationContext == null) {
            return null;
        }

        return applicationContext.getBean(tClass);
    }

    public static <T> T popBean(String name, Class<T> tClass) {
        if (applicationContext == null) {
            return null;
        }

        return applicationContext.getBean(name, tClass);
    }
}
