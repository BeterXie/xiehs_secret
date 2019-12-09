package com.secret.bussiness.biz.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.secret.bussiness.biz.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xiehs
 * @since 2019-11-06
 */
public interface UserDao extends BaseMapper<User> {
    /**
     * 查询用户所有信息
     * @param name
     * @return
     */
    User getUser(@Param("userName")String name);

    /**
     * 用户分页
     * @param page
     * @return
     */
    Page<User> getUserList(@Param("pg") Page<User> page,@Param("map") Map<String,String> map);

}
