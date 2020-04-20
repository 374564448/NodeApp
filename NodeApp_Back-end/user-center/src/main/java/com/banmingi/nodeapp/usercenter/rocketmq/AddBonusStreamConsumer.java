package com.banmingi.nodeapp.usercenter.rocketmq;

import com.banmingi.nodeapp.usercenter.domain.dto.UserAddBonusMsgDTO;
import com.banmingi.nodeapp.usercenter.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.stereotype.Service;

/**
 * @auther 半命i 2020/4/19
 * @description
 */
@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AddBonusStreamConsumer {
    private final UserService userService;
    @StreamListener(Sink.INPUT)
    public void receive(UserAddBonusMsgDTO message) {
       this.userService.addBonus(message);
    }
}
