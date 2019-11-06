package com.secret.bussiness.biz.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.secret.bussiness.biz.dao.UserDao;
import com.secret.bussiness.biz.entity.User;
import com.secret.bussiness.biz.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xiehs
 * @since 2019-11-06
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements IUserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User getUser(String name) {
        User user = userDao.getUser(name);
        return user;
    }
}
