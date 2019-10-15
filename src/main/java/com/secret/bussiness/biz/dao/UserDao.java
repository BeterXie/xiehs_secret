package com.secret.bussiness.biz.dao;

import com.secret.bussiness.biz.entity.User;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xiehs
 * @since 2019-10-10
 */
public interface UserDao extends BaseMapper<User> {

    public User getUser(@Param("userName")String name);

}
