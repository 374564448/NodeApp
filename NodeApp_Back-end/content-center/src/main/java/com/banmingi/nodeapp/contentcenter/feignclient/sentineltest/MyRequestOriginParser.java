/*
package com.banmingi.nodeapp.contentcenter.feignclient.sentineltest;

import com.alibaba.csp.sentinel.adapter.servlet.callback.RequestOriginParser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

*/
/**
 * @auther 半命i 2020/4/17
 * @description sentinel区分来源
 *//*

@Component
public class MyRequestOriginParser implements RequestOriginParser {
    @Override
    public String parseOrigin(HttpServletRequest request) {

        */
/**
         * 来源放在参数中传递
         *  从请求参数中获取名为 origin 的参数并返回
         *  获取不到 origin 参数则抛异常
         *//*

        String origin = request.getParameter("origin");
        if (StringUtils.isBlank(origin)) {
            throw new IllegalArgumentException("origin must be specified");
        }

        return origin;
    }
}
*/
