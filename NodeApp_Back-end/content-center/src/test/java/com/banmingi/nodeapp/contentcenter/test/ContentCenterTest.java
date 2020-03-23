package com.banmingi.nodeapp.contentcenter.test;

import com.banmingi.nodeapp.contentcenter.dao.ShareMapper;
import com.banmingi.nodeapp.contentcenter.domain.entity.Share;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @auther 半命i 2020/3/22
 * @description
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ContentCenterTest {

    @Resource
    private ShareMapper shareMapper;

    @Test
    public void testInsert() {

        Share share = new Share();
        share.setUserId(1);
        share.setTitle("xxx");
        share.setCreateTime(new Date());
        share.setUpdateTime(new Date());
        share.setCover("xxx");
        share.setBuyCount(2);

        this.shareMapper.insertSelective(share);

    }
}
