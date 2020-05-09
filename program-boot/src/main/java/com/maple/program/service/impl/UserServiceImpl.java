package com.maple.program.service.impl;

import com.maple.program.bean.User;
import com.maple.program.mapper.UserMapper;
import com.maple.program.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户中心-用户信息表 服务实现类
 * </p>
 *
 * @author ZhangFZ
 * @since 2020-05-07
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
