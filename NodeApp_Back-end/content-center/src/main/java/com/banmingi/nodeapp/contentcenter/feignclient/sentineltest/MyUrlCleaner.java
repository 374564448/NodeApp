package com.banmingi.nodeapp.contentcenter.feignclient.sentineltest;

import com.alibaba.csp.sentinel.adapter.servlet.callback.UrlCleaner;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * @auther 半命i 2020/4/17
 * @description
 */
@Component
public class MyUrlCleaner implements UrlCleaner {
    @Override
    public String clean(String originUrl) {
        //让 /share/1 与 /share/2 的返回值相同
        // 返回 /share/{number}  而不是两个端口分开
        String[] split = originUrl.split("/");

        return Arrays.stream(split).map(string -> {
            if (NumberUtils.isNumber(string)) {
                return "{number}";
            }
            return string;
        }).reduce((a,b) -> a + "/" +b).orElse("");
    }
}
