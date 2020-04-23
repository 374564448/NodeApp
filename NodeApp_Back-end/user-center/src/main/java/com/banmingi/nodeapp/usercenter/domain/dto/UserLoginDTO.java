package com.banmingi.nodeapp.usercenter.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @auther 半命i 2020/4/22
 * @description
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginDTO {
    /**
     * 头像地址
     */
    private String avatarUrl;
    /**
     * code
     */
    private String code;
    /**
     * 微信昵称
     */
    private String wxNickname;
}
