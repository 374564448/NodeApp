package com.banmingi.nodeapp.contentcenter.controller;

import com.banmingi.nodeapp.contentcenter.domain.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;

/**
 * @auther 半命i 2020/4/7
 * @description
 */
@RestController
@RequestMapping("test")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TestController {


    private final TestService testService;

    private final RestTemplate restTemplate;

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

    @GetMapping("/testRestTemplate/{userId}")
    public UserDTO testRestTemplate(@PathVariable Integer userId) {
        return restTemplate.getForObject("http://user-center/users/{userId}",
                UserDTO.class,
                userId);
    }

    @GetMapping("/tokenRelay/{userId}")
    public ResponseEntity<UserDTO> tokenRelay(@PathVariable Integer userId, HttpServletRequest request) {

        String token = request.getHeader("X-Token");

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("X-Token",token);

        ResponseEntity<UserDTO> exchange = this.restTemplate
                .exchange("http://user-center/users/{userId}",
                        HttpMethod.GET,
                        new HttpEntity<>(httpHeaders),
                        UserDTO.class,
                        userId);
        return exchange;
    }
}
