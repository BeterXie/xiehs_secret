package com.secret.bussiness.dewu.service;

import com.secret.bussiness.dewu.domain.search.SearchJsonRootBean;
import net.sf.json.JSONObject;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description: 获取企业基本信息（含主要人员）
 *
 * @Author: chenyanfeng
 * @Date: 2018-11-23
 * @Time: 下午6:33
 */
public interface ISearchService {

    /**
     * 获取查询结果
     * @param companyName
     * @param pageNum
     * @param pageSize
     * @param sortType
     * @return
     */
    SearchJsonRootBean getSearchResult(String companyName, String pageNum, String pageSize, String sortType);


    /**
     * 获取查询结果
     * @param companyName
     * @param pageNum
     * @param pageSize
     * @return
     */
    SearchJsonRootBean getSearchResult(String companyName, String pageNum, String pageSize);


    /**
     * 获取查询结果
     * @param companyName
     * @return
     */
    SearchJsonRootBean getSearchResult(String companyName);


    JSONObject getProductList(String productCode,Map<String, String> iPparams);

    JSONObject getAPPProductList(String productCode);

}
