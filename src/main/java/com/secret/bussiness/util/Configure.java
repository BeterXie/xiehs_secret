package com.secret.bussiness.util;

import org.springframework.core.env.Environment;

//获取spring 配置文件
public class Configure {


    public static String getString(String key) {
        // String value = PropertiesUtils.getProperty("/application.properties",key);
        String value = SpringContext.getApplicationContext().getBean(Environment.class).getProperty(key);
        return value;
    }

    public static int getInt(String key) {
        return Integer.valueOf(getString(key));
    }

    public static boolean getBoolean(String key) {
        return Boolean.valueOf(getString(key));
    }


    public static boolean isTest() {
        try {
            return Configure.getString("runMode").equalsIgnoreCase("dev");
        } catch (Exception e) {
            return false;
        }
    }


}
