package com.secret.bussiness.biz.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.secret.bussiness.biz.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

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

    Page<User> getUserList(Page<User> page, Map<String,String> map);

    IPage<User> selectPage(Page<User> page, QueryWrapper<User> wrapper);

}
