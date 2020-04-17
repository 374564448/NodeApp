package com.banmingi.nodeapp.contentcenter.configuration;

import feign.Logger;
import org.springframework.context.annotation.Bean;

/**
 * @auther 半命i 2020/4/7
 * @description feign的配置类
 * 别加@Configuration注解,否则必须挪到 @ComponentScan 所扫描的包以外
 */
public class FeignConfiguration {

    /**
     * 设置feign日志级别: NONE  BASIC HEADERS FULL
     * @return
     */
    @Bean
    public Logger.Level level() {
        return Logger.Level.FULL;
    }
}
