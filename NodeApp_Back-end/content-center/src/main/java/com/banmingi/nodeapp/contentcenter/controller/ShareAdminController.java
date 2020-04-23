package com.banmingi.nodeapp.contentcenter.controller;

import com.banmingi.nodeapp.contentcenter.auth.CheckAuthorization;
import com.banmingi.nodeapp.contentcenter.domain.dto.ShareAuditDTO;
import com.banmingi.nodeapp.contentcenter.domain.entity.Share;
import com.banmingi.nodeapp.contentcenter.service.ShareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @auther 半命i 2020/4/18
 * @description
 */
@RestController
@RequestMapping("/admin/shares")
public class ShareAdminController {

    @Autowired
    private ShareService shareService;

    /**
     * 审核指定内容
     * @param id
     * @param auditDTO
     * @return
     */
    @PutMapping("/audit/{id}")
    @CheckAuthorization("admin")
    public Share auditById(@PathVariable(value = "id") Integer id,
                           @RequestBody ShareAuditDTO auditDTO) {
        return this.shareService.auditById(id,auditDTO);
    }
}
