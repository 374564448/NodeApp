package com.banmingi.nodeapp.contentcenter.feignclient;

import com.banmingi.nodeapp.contentcenter.domain.dto.UserDTO;
import com.banmingi.nodeapp.contentcenter.feignclient.fallbackfactory.UserCenterFeignClientFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @auther 半命i 2020/4/7
 * @description
 */
//@FeignClient(name = "user-center",configuration = CenterFeignConfiguration.class)
@FeignClient(name = "user-center",
//fallback = UserCenterFeignClientFallback.class
fallbackFactory = UserCenterFeignClientFallbackFactory.class)
public interface UserCenterFeignClient {
    /**
     *
     * @param id
     * @return
     */
    @GetMapping("/users/{id}")
    UserDTO findById(@PathVariable Integer id);
}
