package com.banmingi.nodeapp.usercenter.service;

import com.banmingi.nodeapp.usercenter.dao.BonusEventLogMapper;
import com.banmingi.nodeapp.usercenter.dao.UserMapper;
import com.banmingi.nodeapp.usercenter.domain.dto.UserAddBonusMsgDTO;
import com.banmingi.nodeapp.usercenter.domain.entity.BonusEventLog;
import com.banmingi.nodeapp.usercenter.domain.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @auther 半命i 2020/3/22
 * @description
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserService {
    private final UserMapper userMapper;
    private final BonusEventLogMapper bonusEventLogMapper;

    /**
     * 根据id查询用户
     * @param id
     * @return
     */
    public User findById(Integer id) {
        return this.userMapper.selectByPrimaryKey(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public void addBonus(UserAddBonusMsgDTO message) {
        //1. 为用户加积分
        Integer userId = message.getUserId();
        Integer bonus = message.getBonus();
        User user = this.userMapper.selectByPrimaryKey(userId);
        user.setBonus(user.getBonus()+bonus);
        this.userMapper.updateByPrimaryKey(user);
        //2. 记录日志到bonus_event_log 里面
        this.bonusEventLogMapper.insert(
                BonusEventLog.builder()
                        .userId(userId)
                        .value(bonus)
                        .event("CONTRIBUTE")
                        .createTime(new Date())
                        .description("投稿加积分")
                        .build());
        log.info("积分添加完毕");
    }
}
