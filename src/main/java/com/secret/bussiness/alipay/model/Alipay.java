package com.secret.bussiness.alipay.model;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author xhs
 * @package com.secret.bussiness.alipay.model
 * @date 2019/10/16 14:40
 */
@Data
@Accessors(chain = true)
public class Alipay {

    /*商户订单号，必填*/
    private String out_trade_no;
    /*支付宝订单号*/
    private String trade_no ="";
    /*订单名称，必填*/
    private String subject;
    /*付款金额，必填*/
    private StringBuffer total_amount;
    /*商品描述，可空*/
    private String body;
    /*超时时间参数*/
    private String timeout_express = "10m";
    private String product_code = "FAST_INSTANT_TRADE_PAY";
}
