package com.banmingi.nodeapp.contentcenter.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @auther 半命i 2020/4/13
 * @description
 */
@Slf4j
@Service
public class TestService {

    @SentinelResource("common")
    public String common(){
        log.info("common...");
        return "common";
    }
}
