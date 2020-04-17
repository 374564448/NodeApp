package com.banmingi.nodeapp.contentcenter.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @auther 半命i 2020/4/7
 * @description
 */
@FeignClient(name = "baidu",url = "http://www.baidu.com")
public interface TestBaiduFeignClient {

    @GetMapping("")
    String index();

}
