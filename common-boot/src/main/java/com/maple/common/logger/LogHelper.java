package com.maple.common.logger;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogHelper {

    //日志类型（0：登录 1：业务 2：接口）
    GlobalConfig.logTypeEnum logType();
}