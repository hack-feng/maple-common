package com.maple.common.mq;

import com.maple.common.bean.MailContent;
import com.maple.common.bean.SystemLog;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author ZhangFZ
 * RabbitMQ 发送信息
 */
@Component
public class MessageSender {

    /**
     * 发送邮件的队列
     */
    public final static String SEND_EMAIL_QUEUES = "sendEmail";

    /**
     * 保存日志的队列
     */
    public final static String SAVE_LOG_QUEUES = "saveLog";


    private static AmqpTemplate amqpTemplate;

    /**
     * 注入amqpTemplate
     */
    @Autowired
    public void setChatService(AmqpTemplate amqpTemplate) {
        MessageSender.amqpTemplate = amqpTemplate;
    }

    /**
     * 将邮件消息放入rabbitMQ "sendEmail" 通道的消息队列
     * @param msg 邮件信息
     */
    public void sendEmail(MailContent msg) {
        amqpTemplate.convertAndSend(SEND_EMAIL_QUEUES, msg);
    }

    /**
     * 将日志消息放入rabbitMQ "saveLog" 通道的消息队列
     * @param log 日志信息
     */
    public void saveLog(SystemLog log){
        amqpTemplate.convertAndSend(SAVE_LOG_QUEUES, log);
    }

}
