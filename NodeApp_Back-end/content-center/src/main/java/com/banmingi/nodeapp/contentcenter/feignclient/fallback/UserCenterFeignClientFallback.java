package com.banmingi.nodeapp.contentcenter.feignclient.fallback;

import com.banmingi.nodeapp.contentcenter.domain.dto.UserDTO;
import com.banmingi.nodeapp.contentcenter.feignclient.UserCenterFeignClient;
import org.springframework.stereotype.Component;

/**
            * @auther 半命i 2020/4/17
            * @description
 */
@Component
public class UserCenterFeignClientFallback implements UserCenterFeignClient {
    /**
     * @param id
     * @return
     */
    @Override
    public UserDTO findById(Integer id) {
        UserDTO userDTO = new UserDTO();
        userDTO.setWxNickname("一个默认用户");
        return userDTO;
    }
}
