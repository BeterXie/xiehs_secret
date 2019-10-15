package com.secret.bussiness.constant;

import java.util.UUID;

/**
 * @author xiehs
 * @package com.secret.bussiness.constant
 * @date 2019/9/24  21:38
 */
public class Constant {

    public static final String JWT_ID = UUID.randomUUID().toString();

    /**
     * 加密密文
     */
    public static final String JWT_SECRET = "tokenlogin";
    public static final int JWT_TTL = 60*60*1000;  //millisecond
}
