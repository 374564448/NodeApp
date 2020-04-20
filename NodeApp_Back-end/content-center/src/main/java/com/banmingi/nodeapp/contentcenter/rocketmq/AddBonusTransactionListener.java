package com.banmingi.nodeapp.contentcenter.rocketmq;

import com.alibaba.fastjson.JSON;
import com.banmingi.nodeapp.contentcenter.dao.RocketMQTransactionLogMapper;
import com.banmingi.nodeapp.contentcenter.domain.dto.ShareAuditDTO;
import com.banmingi.nodeapp.contentcenter.domain.entity.RocketMQTransactionLog;
import com.banmingi.nodeapp.contentcenter.service.ShareService;
import lombok.RequiredArgsConstructor;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;

/**
 * @auther 半命i 2020/4/19
 * @description
 */
//写成和发布半消息一致的group
@RocketMQTransactionListener(txProducerGroup = "tx-add-bonus-group")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AddBonusTransactionListener implements RocketMQLocalTransactionListener {

    private final ShareService shareService;
    private final RocketMQTransactionLogMapper rocketMQTransactionLogMapper;

    /**
     * 执行本地事务. （流程图中第三步）
     * @param message 发送半消息时构建的 message
     * @param o //发送半消息的参数 arg
     * @return
     */
    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message message, Object o) {
        //消息头
        MessageHeaders headers = message.getHeaders();
        //获取 transactionId
        String transactionId = (String) headers.get(RocketMQHeaders.TRANSACTION_ID);
        //获取 share_id
        //get的时候获取到的是字符串
        Integer share_id = Integer.valueOf((String) headers.get("share_id"));
        //获取auditDTO
        String dtoString = (String) headers.get("dto");
        ShareAuditDTO auditDTO = JSON.parseObject(dtoString, ShareAuditDTO.class);

        //执行本地事务
        try {
            this.shareService.auditByIdWithRocketMQLog(share_id, auditDTO,transactionId);
            //本地事务执行成功
            return RocketMQLocalTransactionState.COMMIT;
        } catch (Exception e) {
            return RocketMQLocalTransactionState.ROLLBACK;
        }
    }

    /**
     * 本地事务的检查接口.（流程图中第四步）
     * 检查本地事务的状态.
     * 这里通过查询事务日志表实现
     * @param message 送半消息时构建的 message
     * @return
     */
    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message message) {
        //消息头
        MessageHeaders headers = message.getHeaders();
        //获取 transactionId
        String transactionId = (String) headers.get(RocketMQHeaders.TRANSACTION_ID);

        //查询RocketMQ事务日志表是否有记录
        RocketMQTransactionLog rocketMQTransactionLog = this.rocketMQTransactionLogMapper.selectOne(
                RocketMQTransactionLog.builder()
                        .transactionId(transactionId).build());

        if (rocketMQTransactionLog != null)
            return RocketMQLocalTransactionState.COMMIT;

        return RocketMQLocalTransactionState.ROLLBACK;
    }
}
