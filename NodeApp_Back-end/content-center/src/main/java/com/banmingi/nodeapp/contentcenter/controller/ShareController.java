package com.banmingi.nodeapp.contentcenter.controller;

import com.banmingi.nodeapp.contentcenter.domain.dto.ShareDTO;
import com.banmingi.nodeapp.contentcenter.service.ShareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @auther 半命i 2020/3/22
 * @description
 */
@RestController
@RequestMapping("/shares")
public class ShareController {
    @Autowired
    private ShareService shareService;

    /**
     * 根据id查询详情
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ShareDTO findById(@PathVariable Integer id) {
        return this.shareService.findById(id);
    }
}
