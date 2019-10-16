package com.secret.bussiness.util;

import com.alibaba.fastjson.JSON;
import com.alipay.api.*;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.secret.bussiness.alipay.model.Alipay;
import org.apache.commons.lang.StringUtils;

/**
 * @author xhs
 * @package com.secret.bussiness.util
 * @date 2019/10/16 15:01
 */
public class AlipayUtil {

    public static String connect(Alipay alipay) throws AlipayApiException {

        //构造client
        CertAlipayRequest certAlipayRequest = new CertAlipayRequest();
        /*certAlipayRequest.setServerUrl(gateway);
        certAlipayRequest.setAppId(app_id);
        certAlipayRequest.setPrivateKey(privateKey);
        certAlipayRequest.setFormat("json");
        certAlipayRequest.setCharset(charset);
        certAlipayRequest.setSignType(sign_type);
        certAlipayRequest.setCertPath(app_cert_path);
        certAlipayRequest.setAlipayPublicCertPath(alipay_cert_path);
        certAlipayRequest.setRootCertPath(alipay_root_cert_path);*/
        DefaultAlipayClient alipayClient = new DefaultAlipayClient(certAlipayRequest);

        //发送API请求
        AlipayRequest request = new AlipayTradeQueryRequest();

        AlipayResponse alipayResponse = alipayClient.certificateExecute(request);
        //1、获得初始化的AlipayClient
        /*AlipayClient alipayClient = new DefaultAlipayClient(
                StringUtils.trimToNull(Configure.getString("gatewayUrl")),//支付宝网关
                StringUtils.trimToNull(Configure.getString("app_id")),//appid
                StringUtils.trimToNull(Configure.getString("merchant_private_key")),//商户私钥
                "json", StringUtils.trimToNull(Configure.getString("charset")),//字符编码格式
                StringUtils.trimToNull(Configure.getString("alipay_public_key")),//支付宝公钥
                StringUtils.trimToNull(Configure.getString("sign_type"))//签名方式
        );*/
        //2、设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        //页面跳转同步通知页面路径
        alipayRequest.setReturnUrl(StringUtils.trimToNull(Configure.getString("return_url")));
        // 服务器异步通知页面路径
        alipayRequest.setNotifyUrl(StringUtils.trimToNull(Configure.getString("notify_url")));
        //封装参数
        alipayRequest.setBizContent(JSON.toJSONString(alipay));
        //3、请求支付宝进行付款，并获取支付结果
        String result = alipayClient.pageExecute(alipayRequest).getBody();
        //返回付款信息
        return result;
    }

}
