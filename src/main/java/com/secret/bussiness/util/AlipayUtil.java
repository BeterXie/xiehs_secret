package com.secret.bussiness.util;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.CertAlipayRequest;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradePrecreateModel;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradePrecreateResponse;
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
     * 扫码支付
     * https://doc.open.alipay.com/docs/doc.htm?spm=a219a.7629140.0.0.i0UVZn&treeId=193&articleId=105170&docType=1#s4
     * @param notifyUrl
     * @return
     * @throws AlipayApiException
     */
    public static String tradePrecreatePay(AlipayTradePrecreateModel model, String notifyUrl) throws AlipayApiException{
        AlipayTradePrecreateResponse response = tradePrecreatePayToResponse(model,notifyUrl);
        return response.getBody();
    }
    public static AlipayTradePrecreateResponse tradePrecreatePayToResponse(AlipayTradePrecreateModel model, String notifyUrl) throws AlipayApiException{
        AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();
        //构造client，设置公共请求参数
        DefaultAlipayClient alipayClient = new DefaultAlipayClient(AlipayUtil.setCertAlipayRequest());
        request.setBizModel(model);
        request.setNotifyUrl(notifyUrl);
        return alipayClient.execute(request);
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
        //封装参数
        alipay.setProduct_code("FACE_TO_FACE_PAYMENT");
        request.setBizContent("{" +
                "\"out_trade_no\":\"20150320010101001\"," +
                "\"seller_id\":\"2088102146225135\"," +
                "\"total_amount\":88.88," +
                "\"discountable_amount\":8.88," +
                "\"undiscountable_amount\":80," +
                "\"buyer_logon_id\":\"15901825620\"," +
                "\"subject\":\"Iphone6 16G\"," +
                "      \"goods_detail\":[{" +
                "        \"goods_id\":\"apple-01\"," +
                "\"alipay_goods_id\":\"20010001\"," +
                "\"goods_name\":\"ipad\"," +
                "\"quantity\":1," +
                "\"price\":2000," +
                "\"goods_category\":\"34543238\"," +
                "\"categories_tree\":\"124868003|126232002|126252004\"," +
                "\"body\":\"特价手机\"," +
                "\"show_url\":\"http://www.alipay.com/xxx.jpg\"" +
                "        }]," +
                "\"body\":\"Iphone6 16G\"," +
                "\"product_code\":\"FACE_TO_FACE_PAYMENT\"," +
                "\"operator_id\":\"yx_001\"," +
                "\"store_id\":\"NJ_001\"," +
                "\"disable_pay_channels\":\"pcredit,moneyFund,debitCardExpress\"," +
                "\"enable_pay_channels\":\"pcredit,moneyFund,debitCardExpress\"," +
                "\"terminal_id\":\"NJ_T_001\"," +
                "\"extend_params\":{" +
                "\"sys_service_provider_id\":\"2088511833207846\"," +
                "\"hb_fq_num\":\"3\"," +
                "\"hb_fq_seller_percent\":\"100\"," +
                "\"industry_reflux_info\":\"{\\\\\\\"scene_code\\\\\\\":\\\\\\\"metro_tradeorder\\\\\\\",\\\\\\\"channel\\\\\\\":\\\\\\\"xxxx\\\\\\\",\\\\\\\"scene_data\\\\\\\":{\\\\\\\"asset_name\\\\\\\":\\\\\\\"ALIPAY\\\\\\\"}}\"," +
                "\"card_type\":\"S0JP0000\"" +
                "    }," +
                "\"timeout_express\":\"90m\"," +
                "\"royalty_info\":{" +
                "\"royalty_type\":\"ROYALTY\"," +
                "        \"royalty_detail_infos\":[{" +
                "          \"serial_no\":1," +
                "\"trans_in_type\":\"userId\"," +
                "\"batch_no\":\"123\"," +
                "\"out_relation_id\":\"20131124001\"," +
                "\"trans_out_type\":\"userId\"," +
                "\"trans_out\":\"2088101126765726\"," +
                "\"trans_in\":\"2088101126708402\"," +
                "\"amount\":0.1," +
                "\"desc\":\"分账测试1\"," +
                "\"amount_percentage\":\"100\"" +
                "          }]" +
                "    }," +
                "\"settle_info\":{" +
                "        \"settle_detail_infos\":[{" +
                "          \"trans_in_type\":\"cardAliasNo\"," +
                "\"trans_in\":\"A0001\"," +
                "\"summary_dimension\":\"A0001\"," +
                "\"settle_entity_id\":\"2088xxxxx;ST_0001\"," +
                "\"settle_entity_type\":\"SecondMerchant、Store\"," +
                "\"amount\":0.1" +
                "          }]" +
                "    }," +
                "\"sub_merchant\":{" +
                "\"merchant_id\":\"19023454\"," +
                "\"merchant_type\":\"alipay: 支付宝分配的间连商户编号, merchant: 商户端的间连商户编号\"" +
                "    }," +
                "\"alipay_store_id\":\"2016052600077000000015640104\"," +
                "\"merchant_order_no\":\"20161008001\"," +
                "\"ext_user_info\":{" +
                "\"name\":\"李明\"," +
                "\"mobile\":\"16587658765\"," +
                "\"cert_type\":\"IDENTITY_CARD\"," +
                "\"cert_no\":\"362334768769238881\"," +
                "\"min_age\":\"18\"," +
                "\"fix_buyer\":\"F\"," +
                "\"need_check_info\":\"F\"" +
                "    }," +
                "\"business_params\":{" +
                "\"campus_card\":\"0000306634\"," +
                "\"card_type\":\"T0HK0000\"," +
                "\"actual_order_time\":\"2019-05-14 09:18:55\"" +
                "    }," +
                "\"qr_code_timeout_express\":\"90m\"" +
                "  }");
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
