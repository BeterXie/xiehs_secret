package com.secret.bussiness.alipay.service.impl;

import com.alipay.api.AlipayApiException;
import com.secret.bussiness.alipay.model.Alipay;
import com.secret.bussiness.alipay.service.AlipayService;
import com.secret.bussiness.util.AlipayUtil;
import org.springframework.stereotype.Service;

/**
 * @author xhs
 * @package com.secret.bussiness.alipay.service.impl
 * @date 2019/10/16 16:49
 * 支付宝支付
 */
@Service
public class AlipayServiceImpl implements AlipayService {

    @Override
    public String aliPayBypage(Alipay alipay) throws AlipayApiException {
        return AlipayUtil.aliPayBypage(alipay);
    }

    @Override
    public String aliPayByQrCode(Alipay alipay) throws AlipayApiException {
        return AlipayUtil.aliPayByQrCode(alipay);
    }
}
