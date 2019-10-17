package com.secret.bussiness.util;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.CertAlipayRequest;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.secret.bussiness.alipay.model.Alipay;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author xhs
 * @package com.secret.bussiness.util
 * @date 2019/10/16 15:01
 */
public class AlipayUtil {

    /**
     * 构造client，设置公共请求参数
     * @return
     */
    public static  CertAlipayRequest setCertAlipayRequest(){
        //构造client，设置公共请求参数
        CertAlipayRequest certAlipayRequest = new CertAlipayRequest();
        //支付宝网关
        certAlipayRequest.setServerUrl(StringUtils.trimToNull(Configure.getString("gatewayUrl")));
        //应用ID,APPID，收款账号既是您的APPID对应支付宝账号
        certAlipayRequest.setAppId(StringUtils.trimToNull(Configure.getString("app_id")));
        //商户应用私钥
        certAlipayRequest.setPrivateKey(StringUtils.trimToNull(Configure.getString("private_key")));
        certAlipayRequest.setFormat("json");
        //字符编码格式
        certAlipayRequest.setCharset(StringUtils.trimToNull(Configure.getString("charset")));
        //签名方式
        certAlipayRequest.setSignType(StringUtils.trimToNull(Configure.getString("sign_type")));
        //应用公钥证书路径
        certAlipayRequest.setCertPath(StringUtils.trimToNull(Configure.getString("app_cert_path")));
        //支付宝证书路径
        certAlipayRequest.setAlipayPublicCertPath(StringUtils.trimToNull(Configure.getString("alipay_cert_path")));
        //支付宝根证书路径
        certAlipayRequest.setRootCertPath(StringUtils.trimToNull(Configure.getString("alipay_root_cert_path")));
        return  certAlipayRequest;
    }

    /**
     * 通过跳转支付页面支付的方法
     * @param alipay
     * @return
     * @throws AlipayApiException
     */
    public static String aliPayBypage(Alipay alipay) throws AlipayApiException {
        Logger logger = LoggerFactory.getLogger(AlipayUtil.class);
        logger.info("开始请求支付页面");
        //构造client，设置公共请求参数
        DefaultAlipayClient alipayClient = new DefaultAlipayClient(AlipayUtil.setCertAlipayRequest());
        //发送API请求
        AlipayTradeQueryRequest alipayTradeQueryRequest = new AlipayTradeQueryRequest();
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
        //页面跳转同步通知页面路径
        request.setReturnUrl(StringUtils.trimToNull(Configure.getString("return_url")));
        // 服务器异步通知页面路径
        request.setNotifyUrl(StringUtils.trimToNull(Configure.getString("notify_url")));
        //封装参数
        request.setBizContent(JSON.toJSONString(alipay));
        //3、请求支付宝进行付款，并获取支付结果
        String form = "";
        try {
            form = alipayClient.pageExecute(request).getBody();
        }catch (AlipayApiException e){
                e.printStackTrace();
        }
        //返回付款信息
        logger.info(form);
        return form;
    }

    /**
     * 获取支付二维码
     * @param alipay
     * @return
     * @throws AlipayApiException
     */
    public static String aliPayByQrCode(Alipay alipay) throws AlipayApiException {
        Logger logger = LoggerFactory.getLogger(AlipayUtil.class);
        logger.info("开始请求支付二维码参数");
        //构造client，设置公共请求参数
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayUtil.setCertAlipayRequest());
        //发送API请求
        AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();//创建API对应的request类
        //页面跳转同步通知页面路径
        request.setReturnUrl(StringUtils.trimToNull(Configure.getString("return_url")));
        // 服务器异步通知页面路径
        request.setNotifyUrl(StringUtils.trimToNull(Configure.getString("notify_url")));
        //封装参数
        request.setBizContent(JSON.toJSONString(alipay));
        //3、请求支付宝进行付款，并获取支付结果
        String form = "";
        try {
            form = alipayClient.execute(request).getBody();
        }catch (AlipayApiException e){
            e.printStackTrace();
        }
        //返回付款信息
        logger.info(form);
        return form;
    }

}
