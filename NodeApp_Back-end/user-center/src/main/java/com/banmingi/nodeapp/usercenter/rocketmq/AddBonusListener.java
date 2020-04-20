package com.banmingi.nodeapp.usercenter.rocketmq;

/**
 * @auther 半命i 2020/4/18
 * @description
 */
/*
@Service
@Slf4j
@RocketMQMessageListener(consumerGroup = "consumer-group",topic = "add-bonus")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AddBonusListener implements RocketMQListener<UserAddBonusMsgDTO> {
    private final UserMapper userMapper;
    private final BonusEventLogMapper bonusEventLogMapper;
    */
/**
 * 当收到消息的时候执行的业务
 * @param message
 *//*

    @Override
    public void onMessage(UserAddBonusMsgDTO message) {
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
*/
