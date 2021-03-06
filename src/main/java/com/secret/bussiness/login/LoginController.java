package com.secret.bussiness.login;

import com.secret.bussiness.base.BaseController;
import com.secret.bussiness.biz.entity.User;
import com.secret.bussiness.biz.service.IUserService;
import com.secret.bussiness.constant.Constant;
import com.secret.bussiness.util.JSONUtil;
import com.secret.bussiness.util.JwtUtil;
import io.jsonwebtoken.Claims;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author xiehs
 * @package com.secret.bussiness.login
 * @date 2019/9/23  22:22
 */
@RestController
@RequestMapping("/login")
public class LoginController extends BaseController {

    @Autowired
    private IUserService userService;

    /**
     * 登录
     * @param request
     * @param response
     */
    @RequestMapping(value = "/login.action",params = "login")
    public void Login(HttpServletRequest request, HttpServletResponse response) throws  Exception{
        this.logger.info("开始登录");
        User user = userService.getById(4);
        String subject = JSONUtil.toJSONString(user);
        try {
            JwtUtil util = new JwtUtil();
            String jwt = util.createJWT(Constant.JWT_ID, "xiehs", subject, Constant.JWT_TTL);
            System.out.println("JWT：" + jwt);
            this.renderJson(response,jwt);
        } catch (Exception e) {
            this.renderJson(response,"数据异常");
        }
    }

    @RequestMapping(value = "/login.action",params = "jwt")
    public void getJwtInfo(HttpServletRequest request, HttpServletResponse response) throws  Exception{
        this.logger.info("开始解密");
        JSONObject requestBody = this.getRequestBody(request);
        String token = requestBody.getString("token");
        JwtUtil util = new JwtUtil();
        Claims c = util.parseJWT(token);
        User user2 = JSONUtil.toBean(c.getSubject(),User.class);
        this.renderJson(response,user2);
    }


}
