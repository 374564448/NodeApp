package com.banmingi.nodeapp.contentcenter.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @auther 半命i 2020/4/18
 * @description
 */
@Getter
@AllArgsConstructor
public enum  AuditStatusEnum {
    /**
     * 待审核
     */
    NOT_YET,
    /**
     * 审核通过
     */
    PASS,
    /**
     * 审核不通过
     */
    REJECT;

}
