package com.maple.common.exception;

import com.maple.common.bean.R;
import lombok.extern.slf4j.Slf4j;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * 全局异常分返回消息处理
 *
 * @author ZhangFZ
 */
@Slf4j
public class ExceptionMsgUtil {

    public static R exceptionMsgHandle(Throwable e) {

        String exceptionMsg = e.getMessage();

        if (e.getCause() instanceof SQLIntegrityConstraintViolationException) {
            String split = "'";
            if (exceptionMsg != null && exceptionMsg.contains(split)) {
                return R.failed("该数据已存在，不可以重复使用");
            } else {
                return R.failed(e.getMessage());
            }
        }

        log.error("全局异常信息 ex={}", e.getMessage(), e);

        if (e.getCause() instanceof RuntimeException) {
            return R.failed(e.getMessage());
        }
        return R.failed("系统繁忙，请稍候再试");
    }
}
