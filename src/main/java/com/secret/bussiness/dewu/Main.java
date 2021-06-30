package com.secret.bussiness.dewu;

import com.secret.bussiness.dewu.service.ISearchService;
import com.secret.bussiness.dewu.service.impl.SearchServiceImpl;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @Author: chenyanfeng
 * @Date: 2018-11-25
 * @Time: 下午9:39
 */
public class Main {
    public static void main(String[] args) throws Exception {

        ISearchService searchService = new SearchServiceImpl();
        Map<String, String> ipMap = new HashMap<>();
        ipMap.put("ip","111");
        searchService.getProductList("CQ7740-001",null);

    }
}
