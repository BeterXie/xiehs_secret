package com.secret.bussiness.alipay.service;

import com.alipay.api.AlipayApiException;
import com.secret.bussiness.alipay.model.Alipay;

/**
 * @author xhs
 * @package com.secret.bussiness.alipay.service
 * @date 2019/10/16 16:47
 */
public interface AlipayService {

    String aliPay(Alipay alipay) throws AlipayApiException;
}
