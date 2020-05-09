package com.maple.common.logger;

import com.alibaba.fastjson.JSONObject;
import com.maple.common.bean.GlobalField;
import com.maple.common.bean.SystemLog;
import com.maple.common.exception.ExceptionMsgUtil;
import com.maple.common.mq.MessageSender;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Map;

/**
 * @author ZhangFZ
 * 配置切面类，@Component 注解把切面类放入Ioc容器中
 */
@Aspect
@Component
@Slf4j
public class SystemLogAspect {

    @Value("${spring.application.name}")
    private String appName;

    @Autowired
    private MessageSender messageSender;

    // 定义切点，拦截com.honsupply.demo.controller下的所有方法
//    @Pointcut("execution(public * com.honsupply.*.controller..*.*(..))")
//    public void systemLog (){}

    @Pointcut(value = "@annotation(LogHelper)")
    public void systemLog() {
    }

    @Around(value = "systemLog()&&@annotation(LogHelper)")
    public Object doAround(ProceedingJoinPoint joinPoint) {
        Object obj = new Object();
        try {
            // 定义执行开始时间
            Long startTime;

            // 定义执行结束时间
            Long endTime;
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            SystemLog systemLog = new SystemLog();
            startTime = System.currentTimeMillis();
            try {
                obj = joinPoint.proceed();

                endTime = System.currentTimeMillis();
                systemLog.setRespTime((endTime - startTime) + "");
                systemLog.setSuccess("SUCCESS");
                systemLog.setResults(obj.toString());
            } catch (Throwable throwable) {

                // 异常信息处理
                obj = ExceptionMsgUtil.exceptionMsgHandle(throwable);

                endTime = System.currentTimeMillis();
                systemLog.setRespTime((endTime - startTime) + "");
                systemLog.setSuccess("FALSE");
                systemLog.setErrorMsg(throwable.toString());
            }

            systemLog.setAppName(appName);
            systemLog.setAllMethodName(String.valueOf(joinPoint.getSignature()));
            systemLog.setMethodName(joinPoint.getSignature().getName());
            systemLog.setCreateDate(new Date());
            systemLog.setRequestIp(getIpAdrress(request));
            systemLog.setOperationType(request.getMethod());
//            if (request.getHeader(GlobalField.TOKEN_AUTHORIZATION) != null) {
//                systemLog.setOperateName(JWTUtil.getUsername());
//                systemLog.setOperateId(JWTUtil.getUserId());
//            }
            Map<String, String[]> paramMap = request.getParameterMap();

            //保存参数
            String param = "";
            for (Map.Entry<String, String[]> entry : paramMap.entrySet()) {
                String[] flag = entry.getValue();
                StringBuilder valueFlag = new StringBuilder();
                for (String value : flag) {
                    valueFlag.append(value);
                }
                if (!StringUtils.isEmpty(param)) {
                    param = param + "&";
                }
                param = String.format("%s%s=%s", param, entry.getKey(), valueFlag.toString());
            }
            Object[] bodyParam = joinPoint.getArgs();
            systemLog.setParams(param + JSONObject.toJSONString(bodyParam));
            // 数据库参数text格式，超长截取
            if(systemLog.getParams() != null && systemLog.getParams().length() > 65000){
                systemLog.setParams(systemLog.getParams().substring(65000));
            }
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Method method = signature.getMethod();
            ApiOperation apiOperation = method.getAnnotation(ApiOperation.class);
            if (apiOperation != null) {
                systemLog.setLogDesc(apiOperation.value() + "");
            }

            LogHelper logHelper = method.getAnnotation(LogHelper.class);
            if (logHelper != null) {
                systemLog.setLogType(logHelper.logType() + "");
            }

            log.info(systemLog.toString());

            messageSender.saveLog(systemLog);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }


    /**
     * 获取Ip地址
     */
    private static String getIpAdrress(HttpServletRequest request) {
        String xip = request.getHeader("X-Real-IP");
        String xFor = request.getHeader("X-Forwarded-For");
        String unknown = "unknown";
        if (StringUtils.isNotEmpty(xFor) && !unknown.equalsIgnoreCase(xFor)) {
            //多次反向代理后会有多个ip值，第一个ip才是真实ip
            int index = xFor.indexOf(",");
            if (index != -1) {
                return xFor.substring(0, index);
            } else {
                return xFor;
            }
        }
        xFor = xip;
        if (StringUtils.isNotEmpty(xFor) && !unknown.equalsIgnoreCase(xFor)) {
            return xFor;
        }
        if (StringUtils.isBlank(xFor) || unknown.equalsIgnoreCase(xFor)) {
            xFor = request.getHeader("Proxy-Client-IP");
        }
        if (StringUtils.isBlank(xFor) || unknown.equalsIgnoreCase(xFor)) {
            xFor = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StringUtils.isBlank(xFor) || unknown.equalsIgnoreCase(xFor)) {
            xFor = request.getHeader("HTTP_CLIENT_IP");
        }
        if (StringUtils.isBlank(xFor) || unknown.equalsIgnoreCase(xFor)) {
            xFor = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (StringUtils.isBlank(xFor) || unknown.equalsIgnoreCase(xFor)) {
            xFor = request.getRemoteAddr();
        }
        return xFor;
    }
}
