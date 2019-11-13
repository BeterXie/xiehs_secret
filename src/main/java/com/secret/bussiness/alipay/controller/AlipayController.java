package com.secret.bussiness.alipay.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.domain.AlipayTradePrecreateModel;
import com.secret.bussiness.alipay.model.Alipay;
import com.secret.bussiness.alipay.service.AlipayService;
import com.secret.bussiness.base.BaseController;
import com.secret.bussiness.util.AlipayUtil;
import com.secret.bussiness.util.QrCodeUtil;
import com.secret.bussiness.util.StringUtils;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xhs
 * @package com.secret.bussiness.alipay.controller
 * @date 2019/10/16 16:57
 */
@RestController
@RequestMapping("/alipay")
public class AlipayController  extends BaseController {

    @Autowired
    private AlipayService alipayService;

    /**
     * 返回支付页面
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @PostMapping(value = "/alipay.action",params = "page")
    public void  alipay(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
        this.logger.info("开始支付宝支付");
        Alipay alipay = new Alipay();
        //商品描述
        alipay.setBody(request.getParameter("body"));
        //订单号
        alipay.setOut_trade_no(request.getParameter("out_trade_no"));
        //金额
        alipay.setTotal_amount(new StringBuffer().append(request.getParameter("total_amount")));
        //订单名称
        alipay.setSubject(request.getParameter("subject"));
        String form = "";
        try {
            form = alipayService.aliPayBypage(alipay);
        }catch (AlipayApiException e){
            e.printStackTrace();
        }
        this.renderHTML(response,form);
    }




    /**
     * @name 预下单请求，阿里获取二维码接口
     * @throws AlipayApiException
     * @Param out_trade_no 商户订单号,64个字符以内、只能包含字母、数字、下划线；需保证在商户端不重复
     * @Param total_amount 订单总金额，单位为元，精确到小数点后两位，取值范围[0.01,100000000] 如果同时传入了【打折金额】，【不可打折金额】，
     * 【订单总金额】三者，则必须满足如下条件：【订单总金额】=【打折金额】+【不可打折金额】
     * @Param subject 订单标题
     * @Param store_id 商户门店编号
     * @Param timeout_express 该笔订单允许的最晚付款时间，逾期将关闭交易。
     * 取值范围：1m～15d。m-分钟，h-小时，d-天，1c-当天（1c-当天的情况下，无论交易何时创建，都在0点关闭）。 该参数数值不接受小数点， 如 1.5h，可转换为 90m。
     */
    @RequestMapping(value="/alipay.action",params = "qrCode")
    private Map list(HttpServletRequest request, HttpServletResponse responses) throws AlipayApiException{
        String orderId = request.getParameter("out_trade_no");
        Map<String,Object> map=new HashMap<>();
        if (orderId.isEmpty())
        {
            map.put("type","2");
            map.put("data","订单号不能为空");
            return map;
        }
        Alipay alipay = new Alipay();
        //商品描述
        alipay.setBody(request.getParameter("body"));
        //订单号
        alipay.setOut_trade_no(request.getParameter("out_trade_no"));
        //金额
        alipay.setTotal_amount(new StringBuffer().append(request.getParameter("total_amount")));
        //订单名称
        alipay.setSubject(request.getParameter("subject"));
        String form = "";
        try {
            form = alipayService.aliPayByQrCode(alipay);
        }catch (AlipayApiException e){
            e.printStackTrace();
        }
        JSONObject jsonObject = JSONObject.fromObject(form);
        String qr_code = jsonObject.getJSONObject("alipay_trade_precreate_response").getString("qr_code");
        //流输出
        ServletOutputStream sos = null;
        try {
            sos = responses.getOutputStream();
            //生成二维码
            QrCodeUtil.encode(qr_code, sos);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 扫码支付
     */
    @RequestMapping(value="/alipay.action",params = "qrCodes")
    public void tradePrecreatePay(HttpServletRequest request, HttpServletResponse responses) {
        this.logger.info("扫码支付ss11");
        String subject = "Javen11";
        String totalAmount = "861";
        String storeId = "123ssssss";
        String notifyUrl = "https://www.baidu.com";

        AlipayTradePrecreateModel model = new AlipayTradePrecreateModel();
        model.setSubject(subject);
        model.setTotalAmount(totalAmount);
        model.setStoreId(storeId);
        model.setTimeoutExpress("5m");
        model.setOutTradeNo(StringUtils.getOutTradeNo());
        //流输出
        ServletOutputStream sos = null;
        try {
            String resultStr = AlipayUtil.tradePrecreatePay(model, notifyUrl);
            JSONObject jsonObject = JSONObject.fromObject(resultStr);
            String qr_code = jsonObject.getJSONObject("alipay_trade_precreate_response").getString("qr_code");
            sos = responses.getOutputStream();
            //生成二维码s
            QrCodeUtil.encode(qr_code, sos);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
