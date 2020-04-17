package com.banmingi.nodeapp.usercenter.controller;

import com.banmingi.nodeapp.usercenter.domain.entity.User;
import com.banmingi.nodeapp.usercenter.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @auther 半命i 2020/3/22
 * @description
 */
@RestController
@RequestMapping("/users")
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 根据id查询用户
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public User findById(@PathVariable Integer id) {
        System.out.println("我被请求了");
        return this.userService.findById(id);
    }
}
