package com.banmingi.nodeapp.usercenter.controller;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import com.banmingi.nodeapp.usercenter.auth.CheckLogin;
import com.banmingi.nodeapp.usercenter.domain.dto.JwtTokenRespDTO;
import com.banmingi.nodeapp.usercenter.domain.dto.LoginRespDTO;
import com.banmingi.nodeapp.usercenter.domain.dto.UserLoginDTO;
import com.banmingi.nodeapp.usercenter.domain.dto.UserRespDTO;
import com.banmingi.nodeapp.usercenter.domain.entity.User;
import com.banmingi.nodeapp.usercenter.service.UserService;
import com.banmingi.nodeapp.usercenter.util.JwtOperator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @auther 半命i 2020/3/22
 * @description
 */
@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {

    private final UserService userService;
    private final WxMaService wxMaService;
    private final JwtOperator jwtOperator;

    /**
     * 根据id查询用户
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @CheckLogin
    public User findById(@PathVariable Integer id) {
        System.out.println("我被请求了");
        return this.userService.findById(id);
    }

    /**
     * 模拟生成token
     * @return
     */
    @GetMapping("/gen-token")
    public String genToken() {
        //颁发token
        Map<String,Object> userInfo = new HashMap<>(3);
        userInfo.put("id",1);
        userInfo.put("wxNickname","banmingi");
        userInfo.put("role","admin");
        String token = jwtOperator.generateToken(userInfo);

        return this.jwtOperator.generateToken(userInfo);
    }


    /**
     * 登录
     * @param loginDTO
     * @return
     * @throws WxErrorException
     */
    @PostMapping("/login")
    public LoginRespDTO login(@RequestBody UserLoginDTO loginDTO) throws WxErrorException {
        //微信小程序服务端校验是否已经登录的结果
        WxMaJscode2SessionResult result = wxMaService.getUserService().getSessionInfo(loginDTO.getCode());

        //微信的openId,用户在微信的唯一标识
        String openid = result.getOpenid();

        //看用户是否已经注册到数据库
        //如果未注册,插入返回新user
        //如果已经注册,返回user
        User user = this.userService.login(loginDTO, openid);

        //颁发token
        Map<String,Object> userInfo = new HashMap<>(3);
        userInfo.put("id",user.getId());
        userInfo.put("wxNickname",user.getWxNickname());
        userInfo.put("role",user.getRoles());
        String token = jwtOperator.generateToken(userInfo);
        //日志
        log.info("用户 {} 登录成功,生成的token = {},有效期为 {}",
                user.getWxNickname(),token,jwtOperator.getExpirationTime());

        //构建响应
        return LoginRespDTO.builder().user(
                UserRespDTO.builder()
                .id(user.getId())
                .avatarUrl(user.getAvatarUrl())
                .bonus(user.getBonus())
                .wxNickname(user.getWxNickname())
                .build()
        ).token(
                JwtTokenRespDTO.builder()
                        .token(token)
                        .expirationTime(jwtOperator.getExpirationTime().getTime())
                        .build()
        ).build();
    }

}
