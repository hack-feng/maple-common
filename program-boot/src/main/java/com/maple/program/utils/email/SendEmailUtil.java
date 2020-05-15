package com.maple.program.utils.email;

import com.maple.program.bean.MailContent;
import com.maple.program.mapper.MailContentMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.util.Date;

/**
 * @author ZhangFZ
 * Mq发送邮件信息
 */
@Service
public class SendEmailUtil {

    @Value("${spring.mail.username}")
    private String from;

    private final JavaMailSender mailSender;

    private final MailContentMapper mailContentMapper;

    @Autowired
    public SendEmailUtil(MailContentMapper mailContentMapper, JavaMailSender mailSender) {
        this.mailContentMapper = mailContentMapper;
        this.mailSender = mailSender;
    }

    @Async
    public void sendEmail(MailContent msg){
        try {
            System.setProperty("mail.mime.splitlongparameters", "false");
            // 定义邮件信息
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper;
            helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            msg.setSendFrom(from);
            helper.setTo(msg.getSendTo());
            if(msg.getSubject() == null){
                helper.setSubject("无主题");
                msg.setSubject("无主题");
            }else{
                helper.setSubject(msg.getSubject());
            }
            if(StringUtils.isNotBlank(msg.getCcTo())){
                helper.setCc(msg.getCcTo());
            }

            if(StringUtils.isNotBlank(msg.getContent())){
                helper.setText(msg.getContent(), true);
            }else{
                helper.setText("");
            }

            // 如果存在附件，定义邮件的附件
            if(StringUtils.isNotBlank(msg.getAttachment())){
                FileSystemResource file = new FileSystemResource(msg.getAttachment());
                helper.addAttachment(file.getFilename(), file);
            }
            mailSender.send(message);
            // 修改状态为发送
            msg.setStatus(MailEnum.MailStatus.SEND.code);
        } catch (Exception e) {
            e.printStackTrace();
            // 修改状态为发送
            msg.setComment(e.getMessage());
            msg.setStatus(MailEnum.MailStatus.SEND_FAILED.code);
        } finally {
            if(StringUtils.isBlank(msg.getTempId())){
                msg.setTempId("无模板");
            }
            if(StringUtils.isBlank(msg.getRefId())){
                msg.setRefId("无RefId");
            }
            if(StringUtils.isBlank(msg.getVersion())){
                msg.setVersion("1.0");
            }
            msg.setUpdatedDate(new Date());
            mailContentMapper.insert(msg);
        }
    }
}
