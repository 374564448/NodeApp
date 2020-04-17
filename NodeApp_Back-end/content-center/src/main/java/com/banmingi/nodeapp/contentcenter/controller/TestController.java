package com.banmingi.nodeapp.contentcenter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @auther 半命i 2020/4/7
 * @description
 */
@RestController
@RequestMapping("test")
public class TestController {

    @Autowired
    private TestService testService;

    @GetMapping("/test-a")
    public String testA() {
        this.testService.common();
        return "testA";
    }
    @GetMapping("/test-b")
    public String testB() {
        this.testService.common();
        return "testB";
    }
}
