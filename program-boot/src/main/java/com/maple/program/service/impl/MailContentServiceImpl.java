package com.maple.program.service.impl;

import com.maple.program.bean.MailContent;
import com.maple.program.mapper.MailContentMapper;
import com.maple.program.service.IMailContentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maple.program.utils.email.SendEmailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 基础管理-邮件发送内容表 服务实现类
 * </p>
 *
 * @author ZhangFZ
 * @since 2020-05-09
 */
@Service
public class MailContentServiceImpl extends ServiceImpl<MailContentMapper, MailContent> implements IMailContentService {

    private final SendEmailUtil sendEmailUtil;

    @Autowired
    public MailContentServiceImpl(SendEmailUtil sendEmailUtil) {
        this.sendEmailUtil = sendEmailUtil;
    }

    @Override
    public boolean sendEmail() {
        MailContent mailContent = new MailContent();



        sendEmailUtil.sendEmail(mailContent);
        return false;
    }
}
