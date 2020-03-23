package com.banmingi.nodeapp.contentcenter.service;

import com.banmingi.nodeapp.contentcenter.dao.UserMapper;
import com.banmingi.nodeapp.contentcenter.domain.entity.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @auther 半命i 2020/3/22
 * @description
 */
@Service
public class UserService {
    @Resource
    private UserMapper userMapper;

    /**
     * 根据id查询用户
     * @param id
     * @return
     */
    public User findById(Integer id) {
        return this.userMapper.selectByPrimaryKey(id);
    }
}
