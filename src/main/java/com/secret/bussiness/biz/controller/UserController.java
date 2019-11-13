package com.secret.bussiness.biz.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.secret.bussiness.base.BaseController;
import com.secret.bussiness.biz.entity.User;
import com.secret.bussiness.biz.service.IUserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xiehs
 * @since 2019-11-06
 */
@Controller
@RequestMapping("/biz/user")
public class UserController extends BaseController {

    @Autowired
    private IUserService userService;

    @RequestMapping(value = "/getUser.action")
    public void getUser(HttpServletRequest request, HttpServletResponse response) throws Exception{
        String names = request.getParameter("name");
        if(StringUtils.isBlank(names)){
            this.renderJson(response,"信息有误");
            return;
        }
        this.logger.info("开始获取用户信息");
        User name = userService.getUser(names);
        this.renderJson(response,name);
    }

    @RequestMapping(value = "/getUserList.action")
    public void getUserList(HttpServletRequest request, HttpServletResponse response) throws Exception{
        String current = request.getParameter("current");
        String size = request.getParameter("size");
        String name = request.getParameter("name");
        if(StringUtils.isBlank(current) || StringUtils.isBlank(size)){
            this.renderJson(response,"信息有误");
            return;
        }
        this.logger.info("开始获取用户信息列表");
        Page<User> page =new Page<>();
        Map<String,String> map = new HashMap();
        map.put("name",name);
        page.setCurrent(Integer.parseInt(current));
        page.setSize(Integer.parseInt(size));
        Page<User> userList = userService.getUserList(page,map);
        this.renderJson(response,userList);
    }

    @RequestMapping(value = "/selectPage.action")
    public void selectPage(HttpServletRequest request, HttpServletResponse response) throws Exception{
        String current = request.getParameter("current");
        String size = request.getParameter("size");
        String name = request.getParameter("name");
        if(StringUtils.isBlank(current) || StringUtils.isBlank(size)){
            this.renderJson(response,"信息有误");
            return;
        }
        this.logger.info("开始获取用户信息列表");
        Page<User> page =new Page<>(Long.valueOf(current),Long.valueOf(size));
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.lambda().like(User::getNikeName,name);
        IPage<User> userList = userService.selectPage(page,wrapper);
        this.renderJson(response,userList);
    }

}

