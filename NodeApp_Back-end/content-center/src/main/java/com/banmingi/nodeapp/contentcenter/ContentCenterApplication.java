package com.banmingi.nodeapp.contentcenter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @auther 半命i 2020/3/21
 * @description
 */
@SpringBootApplication
@MapperScan("com.banmingi.nodeapp.contentcenter")
@EnableFeignClients//(defaultConfiguration = CenterFeignConfiguration.class)
@EnableDiscoveryClient
public class ContentCenterApplication {
    public static void main(String[] args) {
        SpringApplication.run(ContentCenterApplication.class);
    }

/*  @Bean
    @LoadBalanced
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }*/
}
