package com.secret.bussiness.biz.service;

import com.secret.bussiness.biz.entity.User;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xiehs
 * @since 2019-10-10
 */
public interface IUserService extends IService<User> {

    public User getUser(String name);



}
