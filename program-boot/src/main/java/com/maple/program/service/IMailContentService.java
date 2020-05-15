package com.maple.program.service;

import com.maple.program.bean.MailContent;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 基础管理-邮件发送内容表 服务类
 * </p>
 *
 * @author ZhangFZ
 * @since 2020-05-09
 */
public interface IMailContentService extends IService<MailContent> {

    boolean sendEmail();
}
