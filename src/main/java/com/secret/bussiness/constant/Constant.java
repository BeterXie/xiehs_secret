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


    /**
     * 支付宝支付返回接口返回状态码
     */
    public static final String ALIPAY_SUCCESS = "10000"; // 成功
    public static final String ALIPAY_PAYING  = "10003"; // 用户支付中
    public static final String ALIPAY_FAILED  = "40004"; // 失败
    public static final String ALIPAY_ERROR   = "20000"; // 系统异常
}
