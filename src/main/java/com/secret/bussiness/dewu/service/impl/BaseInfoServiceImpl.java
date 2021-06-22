package com.secret.bussiness.dewu.service.impl;

import com.google.gson.Gson;
import com.secret.bussiness.dewu.constant.UrlConstant;
import com.secret.bussiness.dewu.domain.baseinfo.BaseInfoJsonRootBean;
import com.secret.bussiness.dewu.service.IBaseInfoService;
import com.secret.bussiness.dewu.utils.HttpUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @Author: chenyanfeng
 * @Date: 2018-11-25
 * @Time: 下午10:22
 */
public class BaseInfoServiceImpl implements IBaseInfoService {

    @Override
    public BaseInfoJsonRootBean getBaseInfoResult(String companyId) {
        try {
            if (StringUtils.isBlank(companyId)) {
                return null;
            }

            // 拼接URL
            String url = UrlConstant.BASE_INFO_URL + companyId;
            HttpResponse response = HttpUtils.get(url, null);

            String s = EntityUtils.toString(response.getEntity());
            Gson gson = new Gson();
            BaseInfoJsonRootBean baseInfoJsonRootBean = gson.fromJson(s, BaseInfoJsonRootBean.class);

            return baseInfoJsonRootBean;
        }catch (Exception e){

        }
        return null;
    }
}
