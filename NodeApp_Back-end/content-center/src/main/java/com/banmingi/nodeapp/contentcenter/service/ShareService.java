package com.banmingi.nodeapp.contentcenter.service;

import com.alibaba.fastjson.JSON;
import com.banmingi.nodeapp.contentcenter.dao.RocketMQTransactionLogMapper;
import com.banmingi.nodeapp.contentcenter.dao.ShareMapper;
import com.banmingi.nodeapp.contentcenter.domain.dto.ShareAuditDTO;
import com.banmingi.nodeapp.contentcenter.domain.dto.ShareDTO;
import com.banmingi.nodeapp.contentcenter.domain.dto.UserDTO;
import com.banmingi.nodeapp.contentcenter.domain.dto.messaging.UserAddBonusMsgDTO;
import com.banmingi.nodeapp.contentcenter.domain.entity.RocketMQTransactionLog;
import com.banmingi.nodeapp.contentcenter.domain.entity.Share;
import com.banmingi.nodeapp.contentcenter.domain.enums.AuditStatusEnum;
import com.banmingi.nodeapp.contentcenter.feignclient.UserCenterFeignClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.UUID;

/**
 * @auther 半命i 2020/3/22
 * @description
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ShareService {

    private final ShareMapper shareMapper;
    private final UserCenterFeignClient userCenterFeignClient;
    private final RocketMQTemplate rocketMQTemplate;
    private final RocketMQTransactionLogMapper rocketMQTransactionLogMapper;
    private final Source source;
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

    /**
     * 审核指定内容
     * @param id
     * @param auditDTO
     * @return
     */
    public Share auditById(Integer id, ShareAuditDTO auditDTO) {

        //1. 查询 share 是否存在,不存在或者当前的audit_status != NOT_YET,那么就抛异常
        Share share = this.shareMapper.selectByPrimaryKey(id);
        if (share == null) {
            throw new IllegalArgumentException("参数非法!该分享不存在!");
        }
        if (!Objects.equals("NOT_YET",share.getAuditStatus())) {
            throw new IllegalArgumentException("参数非法!该分享已审核通过或未通过!");
        }

        //3. 如果是PASS,那么发送消息给rockerMQ,让用户中心去消费,为发布人添加积分
        //异步执行
        //3.1 发送半消息
        if (AuditStatusEnum.PASS.equals(auditDTO.getAuditStatusEnum())) {
            String transactionId = UUID.randomUUID().toString();

            this.source.output().send(
                    MessageBuilder.withPayload(
                    //消息体
                    UserAddBonusMsgDTO.builder().userId(share.getUserId()).bonus(50).build())
                    //消息头 有妙用
                    .setHeader(RocketMQHeaders.TRANSACTION_ID, transactionId)
                    .setHeader("share_id",id)

                    //send方法不是传 arg, 但是必须用到,所以我们把arg放到Message的Header中即可
                     //header中传的对象,在get的时候拿到的是字符串,所以我们把对象转换成Json字符串
                    .setHeader("dto", JSON.toJSONString(auditDTO))
                    .build());

           /* this.rocketMQTemplate.sendMessageInTransaction(
                    "tx-add-bonus-group", //group
                    "add-bonus", //topic
                    MessageBuilder.withPayload(
                            //消息体
                            UserAddBonusMsgDTO.builder().userId(share.getUserId()).bonus(50).build())
                            //消息头 有妙用
                            .setHeader(RocketMQHeaders.TRANSACTION_ID, transactionId)
                            .setHeader("share_id",id)
                    .build(),
                    //arg 有大用处
                    auditDTO
            );*/

        } else {
            //如果是REJECT(审核不通过),直接更新数据库中分享的状态即可
            this.auditByIdInDB(id,auditDTO);
        }

        return share;
    }

    /**
     * 审核资源,将状态设为PASS/REJECT
     * @param id
     * @param auditDTO
     */
    public void auditByIdInDB(Integer id,ShareAuditDTO auditDTO) {
        Share share = Share.builder()
                .id(id)
                .auditStatus(auditDTO.getAuditStatusEnum().toString())
                .reason(auditDTO.getReason())
                .build();
        this.shareMapper.updateByPrimaryKeySelective(share);
    }

    /**
     * 审核资源,将状态设为PASS/REJECT,并记录事务日志
     * @param id
     * @param auditDTO
     * @param transactionId
     */
    @Transactional(rollbackFor = Exception.class)
    public void auditByIdWithRocketMQLog(Integer id,ShareAuditDTO auditDTO,String transactionId) {
        this.auditByIdInDB(id,auditDTO);
        this.rocketMQTransactionLogMapper.insertSelective(
                RocketMQTransactionLog.builder()
                        .transactionId(transactionId)
                        .log("审核分享...")
                        .build()
        );
    }
}
