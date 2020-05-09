package com.maple.common.utils;

import com.maple.common.bean.R;
import com.maple.common.exception.ExceptionMsgUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author ZhangFZ
 * 配置切面类，@Component 注解把切面类放入Ioc容器中
 */
@Aspect
@Component
@Slf4j
public class RespTimeAspect {

    /**
     * 定义切点，拦截com.honsupply.*.controller下的所有方法
     */
    @Pointcut("execution(public * com.maple.*.controller..*.*(..))")
    public void respTime (){}

    @Around(value = "respTime()")
    public Object doAround(ProceedingJoinPoint joinPoint) {
        Object obj;
        Long respTime;
        // 定义执行开始时间
        Long startTime = System.currentTimeMillis();
        // 定义执行结束时间
        Long endTime;
        try {
            obj = joinPoint.proceed();
            endTime = System.currentTimeMillis();
        } catch (Throwable throwable) {
            // 异常信息处理
            obj = ExceptionMsgUtil.exceptionMsgHandle(throwable);
            endTime = System.currentTimeMillis();
        }

        respTime = endTime - startTime;
        if(obj instanceof R){
            R resultVO = (R) obj;
            resultVO.setRespTime(respTime);
            obj = resultVO;
        }
        return obj;
    }
}
