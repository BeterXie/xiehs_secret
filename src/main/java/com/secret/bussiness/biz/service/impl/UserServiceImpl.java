package com.secret.bussiness.biz.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.secret.bussiness.biz.dao.UserDao;
import com.secret.bussiness.biz.entity.User;
import com.secret.bussiness.biz.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

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

    @Override
    public Page<User> getUserList(Page<User> page, Map<String,String> map) {
        return userDao.getUserList(page,map);
    }

    @Override
    public IPage<User> selectPage(Page<User> page, QueryWrapper<User> wrapper) {
        return userDao.selectPage(page, wrapper);
    }
}
