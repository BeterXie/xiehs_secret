package com.secret.bussiness.config;

/**
 * @author xhs
 * @package com.secret.bussiness.config
 * @date 2019/10/10 11:29
 */

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 *  mybatisPlus 配置类，使其加载配置文件
 *
 */
@Configuration
@ImportResource(locations = {"classpath:/mybatis/spring-mybatis.xml"})
public class MybatisPlusConfig {
}
