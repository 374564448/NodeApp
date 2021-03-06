package com.banmingi.nodeapp.contentcenter.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @auther 半命i 2020/4/23
 * @description
 */
public class TestRestTemplateTokenRelayInterceptor implements ClientHttpRequestInterceptor {

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        //1. 从header里面获取token
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes attributes = (ServletRequestAttributes)requestAttributes;
        HttpServletRequest httpRequest = attributes.getRequest();

        String token = httpRequest.getHeader("X-Token");

        HttpHeaders headers = request.getHeaders();
        headers.add("X-Token",token);

        //保证请求继续执行
        return execution.execute(request,body);
    }
}
