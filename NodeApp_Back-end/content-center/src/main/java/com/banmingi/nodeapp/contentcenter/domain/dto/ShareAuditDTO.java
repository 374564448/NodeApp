package com.banmingi.nodeapp.contentcenter.domain.dto;

import com.banmingi.nodeapp.contentcenter.domain.enums.AuditStatusEnum;
import lombok.Data;

/**
 * @auther 半命i 2020/4/18
 * @description
 */
@Data
public class ShareAuditDTO {
    /**
     * 审核状态
     */
    private AuditStatusEnum auditStatusEnum;
    /**
     * 原因
     */
    private String reason;
}
