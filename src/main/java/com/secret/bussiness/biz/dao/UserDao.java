package com.secret.bussiness.biz.dao;

import com.secret.bussiness.biz.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xiehs
 * @since 2019-11-06
 */
public interface UserDao extends BaseMapper<User> {

    User getUser(@Param("userName")String name);

}
