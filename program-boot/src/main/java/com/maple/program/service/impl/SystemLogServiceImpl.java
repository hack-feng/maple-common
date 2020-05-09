package com.maple.program.service.impl;

import com.maple.program.bean.SystemLog;
import com.maple.program.mapper.SystemLogMapper;
import com.maple.program.service.ISystemLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 日志信息-系统日志表 服务实现类
 * </p>
 *
 * @author ZhangFZ
 * @since 2020-05-07
 */
@Service
public class SystemLogServiceImpl extends ServiceImpl<SystemLogMapper, SystemLog> implements ISystemLogService {

}
