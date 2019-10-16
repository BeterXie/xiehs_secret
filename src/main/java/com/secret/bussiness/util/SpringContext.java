package com.secret.bussiness.util;


import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import javax.servlet.ServletContext;

@Service
public class SpringContext implements ApplicationContextAware, InitializingBean {

    public SpringContext(){
        System.out.print("init springContext");
    }

    private static ApplicationContext applicationContext;

    private static ServletContext servletContext;

    @Override
    public void setApplicationContext(ApplicationContext ac)
            throws BeansException {
        applicationContext = ac;
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static Object getBean(String name) {
        return applicationContext.getBean(name);
    }

    public static Object getBean(Class cla) {
        return applicationContext.getBean(cla);
    }


    public void setServletContext(ServletContext sc) {
        servletContext = sc;
    }

    public static ServletContext getServletContext() {
        return servletContext;
    }

    public static String getRealPath() {
        if (servletContext == null) {
            return System.getProperty("java.io.tmpdir");
        }
        return servletContext.getRealPath("/");
    }


    @Override
    public void afterPropertiesSet() throws Exception {


    }
}