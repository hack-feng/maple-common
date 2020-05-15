package com.maple.program.controller;


import com.maple.common.bean.R;
import com.maple.program.service.IMailContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 基础管理-邮件发送内容表 前端控制器
 * </p>
 * @author ZhangFZ
 * @since 2020-05-09
 */
@RestController
@RequestMapping("/mailContent")
public class MailContentController {

    private final IMailContentService mailContentService;

    @Autowired
    public MailContentController(IMailContentService mailContentService) {
        this.mailContentService = mailContentService;
    }

    @GetMapping("/sendEmail")
    public R sendEmail(){
        boolean isOk = mailContentService.sendEmail();
        return R.isOk(isOk, "发送邮件");
    }

}

