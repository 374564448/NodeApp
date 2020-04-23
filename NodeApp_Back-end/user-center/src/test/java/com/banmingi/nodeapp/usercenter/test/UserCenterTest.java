package com.banmingi.nodeapp.usercenter.test;

import com.banmingi.nodeapp.usercenter.dao.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @auther 半命i 2020/3/22
 * @description
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserCenterTest {

    @Resource
    private UserMapper userMapper;

    @Test
    public void testInsert() {


    }
}
