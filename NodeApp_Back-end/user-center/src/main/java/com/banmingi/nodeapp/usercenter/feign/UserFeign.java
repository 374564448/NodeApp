package com.banmingi.nodeapp.usercenter.feign;

import com.banmingi.nodeapp.usercenter.domain.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @auther 半命i 2020/3/31
 * @description
 */
@FeignClient(value = "user-center",path = "users")
public interface UserFeign {
    /**
     * 根据id查询用户
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    User findById(@PathVariable Integer id);
}
