package com.maple.program.utils.email;

import lombok.AllArgsConstructor;

/**
 * 邮件模板的名称
 * @author ZhangFZ
 * @date 2019-12-27 16:20
 */
public class MailEnum {

    @AllArgsConstructor
    public enum MailType {
        /**
         * 邮件类型
         */
        INTERNAL_MAIL(0, "内部邮件"),
        EXTERNAL_MAIL(1, "外部邮件");
        /**
         * 编码
         */
        public final Integer code;
        /**
         * 描述
         */
        public final String desc;
    }

    @AllArgsConstructor
    public enum MailStatus{
        /**
         * 邮件发送状态
         */
        NOT_SEND(0, "未发送"),
        SEND(1, "已发送"),
        SEND_FAILED(1, "发送失败");
        /**
         * 编码
         */
        public final Integer code;
        /**
         * 描述
         */
        public final String desc;
    }
}
