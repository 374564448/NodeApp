package com.banmingi.nodeapp.contentcenter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.support.MessageBuilder;
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

    @Autowired
    private Source source;

    @GetMapping("/test-stream")
    public String testStream() {
        this.source.output().send(MessageBuilder.withPayload("消息体").build());
        return "success";
    }
}
