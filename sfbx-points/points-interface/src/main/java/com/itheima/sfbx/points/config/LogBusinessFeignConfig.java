package com.itheima.sfbx.points.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName DictFenginConfig.java
 * @Description feign的最优化配置
 */
@EnableFeignClients(basePackages = "com.itheima.sfbx.points.feign")
@Configuration
public class LogBusinessFeignConfig {
}
