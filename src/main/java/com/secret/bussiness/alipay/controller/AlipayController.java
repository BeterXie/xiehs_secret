package com.secret.bussiness.alipay.controller;

import com.alipay.api.AlipayApiException;
import com.secret.bussiness.alipay.model.Alipay;
import com.secret.bussiness.alipay.service.AlipayService;
import com.secret.bussiness.base.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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


    @PostMapping(value = "/alipay.action")
    public void  alipay(HttpServletRequest request, HttpServletResponse response) throws AlipayApiException{
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
        String s = alipayService.aliPay(alipay);
        try {
            this.renderJson(response,s);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
