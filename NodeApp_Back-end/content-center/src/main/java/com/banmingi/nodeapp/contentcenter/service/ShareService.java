package com.banmingi.nodeapp.contentcenter.service;

import com.banmingi.nodeapp.contentcenter.dao.ShareMapper;
import com.banmingi.nodeapp.contentcenter.domain.dto.ShareDTO;
import com.banmingi.nodeapp.contentcenter.domain.dto.UserDTO;
import com.banmingi.nodeapp.contentcenter.domain.entity.Share;
import com.banmingi.nodeapp.contentcenter.feignclient.UserCenterFeignClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @auther 半命i 2020/3/22
 * @description
 */
@Slf4j
@Service
public class ShareService {
    @Resource
    private ShareMapper shareMapper;
    @Autowired
    private UserCenterFeignClient userCenterFeignClient;


    /**
     *根据id查询详情
     * @param id
     * @return
     */
    public ShareDTO findById(Integer id) {
        //根据id获取分享详情
        Share share = this.shareMapper.selectByPrimaryKey(id);
        //发布人id
        Integer userId = share.getUserId();

        UserDTO userDTO = this.userCenterFeignClient.findById(userId);


        //消息的装配
        ShareDTO shareDTO = new ShareDTO();
        BeanUtils.copyProperties(share,shareDTO);
        shareDTO.setWxNickname(userDTO.getWxNickname());

        return shareDTO;

    }
}
