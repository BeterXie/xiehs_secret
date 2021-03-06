package com.secret.bussiness.dewu.service.impl;

import com.google.gson.Gson;
import com.secret.bussiness.dewu.DewuProductSearch;
import com.secret.bussiness.dewu.constant.UrlConstant;
import com.secret.bussiness.dewu.domain.search.SearchJsonRootBean;
import com.secret.bussiness.dewu.service.ISearchService;
import com.secret.bussiness.dewu.utils.DewuUtils;
import com.secret.bussiness.dewu.utils.HttpUtils;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @Author: chenyanfeng
 * @Date: 2018-11-23
 * @Time: 下午6:34
 */
public class SearchServiceImpl implements ISearchService {

    protected static Logger logger = LoggerFactory.getLogger(SearchServiceImpl.class);

    private String url = UrlConstant.SEARCH_RUL;
    private String dewuUrl = UrlConstant.DEWU_SEARCH_RUL;

    private static final String DEFAULT_PAGE_NUM = "1";
    private static final String DEFAULT_PAGE_SIZE = "10";
    private static final String DEFAULT_SORT_TYPE = "0";


    @Override
    public SearchJsonRootBean getSearchResult(String companyName, String pageNum, String pageSize, String sortType) {
        try {
            if (StringUtils.isBlank(companyName)) {
                return null;
            }
            // 设置默认参数
            if ( StringUtils.isBlank(pageNum) ) {
                pageNum = DEFAULT_PAGE_NUM;
            }
            if ( StringUtils.isBlank(pageSize) ) {
                pageSize = DEFAULT_PAGE_SIZE;
            }
            if ( StringUtils.isBlank(sortType) ) {
                sortType = DEFAULT_SORT_TYPE;
            }
            HashMap<String, String> paramsMap = new HashMap<String, String>();
            paramsMap.put("pageNum", pageNum);
            paramsMap.put("pageSize", pageSize);

            // 拼接URL
            url += URLEncoder.encode(companyName, "UTF-8");

            String response = HttpUtils.get(url, paramsMap);
            Gson gson = new Gson();
            SearchJsonRootBean searchJsonRootBean = gson.fromJson(response, SearchJsonRootBean.class);

            return searchJsonRootBean;
        }catch (Exception e){

        }
        return null;
    }



    @Override
    public SearchJsonRootBean getSearchResult(String companyName, String pageNum, String pageSize) {
        return getSearchResult(companyName, pageNum, pageSize, null);
    }

    @Override
    public SearchJsonRootBean getSearchResult(String companyName) {
        return getSearchResult(companyName, null, null, null);
    }

    @Override
    public  JSONObject  getProductList(String productCode,Map<String, String> iPparams){
        try {
            if (StringUtils.isBlank(productCode)) {
                return null;
            }
            HashMap<String, String> paramsMap = new HashMap<String, String>();
            paramsMap.put("title", productCode);
            paramsMap.put("page", "0");
            paramsMap.put("sortType", "0");
            paramsMap.put("sortMode", "1");
            paramsMap.put("limit", "20");
            paramsMap.put("showHot", "1");
            paramsMap.put("isAggr", "1");
            //paramsMap.put("sign", "bcbd4b331418525c97d99560d2b7c99d");
            String sign = DewuUtils.getSign(paramsMap);
            paramsMap.put("sign", sign);
            // 拼接URL
            //url += URLEncoder.encode(productCode, "UTF-8");
            String res = null;
            JSONObject content = null;
            if(iPparams == null){
                res = HttpUtils.get(dewuUrl, paramsMap);
            }else {
                res = HttpUtils.get(dewuUrl, paramsMap,iPparams);
            }
            if( res == null || "Bad Gateway: app.dewu.com:443".equals(res)){
                res =null;
            }
            logger.info("res:"+res);
            if(res != null && res.contains("data")){
                content = JSONObject.fromObject(res);
            }
            if(content == null || (content.containsKey("code") && "485".equals(content.getString("code"))) || !content.getJSONObject("data").containsKey("productList")){
                content = this.getProductList(productCode, iPparams);
            }
            return  content;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public JSONObject getAPPProductList(String productCode) {
        try {
            if (StringUtils.isBlank(productCode)) {
                return null;
            }
            HashMap<String, String> paramsMap = new HashMap<String, String>();
            paramsMap.put("page", "0");
            paramsMap.put("title", productCode);
            paramsMap.put("sortMode", "1");
            paramsMap.put("sortType", "0");
            paramsMap.put("limit", "20");
            paramsMap.put("showHot", "1");
            paramsMap.put("isAggr", "1");
            //paramsMap.put("sign", "bcbd4b331418525c97d99560d2b7c99d");
            String sign = DewuUtils.getSign(paramsMap);
            paramsMap.put("sign", sign);

            // 拼接URL
            //url += URLEncoder.encode(productCode, "UTF-8");

            HttpResponse response = HttpUtils.getDewuAPP(dewuUrl, paramsMap);
            String s = EntityUtils.toString(response.getEntity());
            JSONObject content = JSONObject.fromObject(s);
            return  content;
        }catch (Exception e){

        }
        return null;
    }
}
