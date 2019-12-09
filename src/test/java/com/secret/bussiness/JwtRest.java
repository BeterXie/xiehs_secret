package com.secret.bussiness;

import com.secret.bussiness.biz.entity.User;
import com.secret.bussiness.constant.Constant;
import com.secret.bussiness.util.JSONUtil;
import com.secret.bussiness.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.junit.Test;

/**
 * @author xhs
 * @package com.secret.bussiness
 * @date 2019/12/4 10:21
 */
public class JwtRest {

    @Test
    public void test(){
        User user = new User();
        user.setAge(18);
        user.setRealName("小11");
        user.setNikeName("真实姓名");
        String subject = JSONUtil.toJSONString(user);

        try {
            JwtUtil util = new JwtUtil();
            String jwt = util.createJWT(Constant.JWT_ID, "Anson", subject, Constant.JWT_TTL);
            System.out.println("JWT：" + jwt);

            System.out.println("\n解密\n");

            Claims c = util.parseJWT(jwt);
            System.out.println(c.getId());
            System.out.println(c.getIssuedAt());
            System.out.println(c.getSubject());
            User user2 = JSONUtil.toBean(c.getSubject(),User.class);
            System.out.println("user2 nickName:"+user2.getNikeName());
            System.out.println(c.getIssuer());
            System.out.println(c.get("uid", String.class));
            System.out.println(c.get("user_name"));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
