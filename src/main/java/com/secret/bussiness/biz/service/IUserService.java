package com.secret.bussiness.biz.service;

import com.secret.bussiness.biz.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xiehs
 * @since 2019-11-06
 */
public interface IUserService extends IService<User> {

    User getUser(String name);

}
