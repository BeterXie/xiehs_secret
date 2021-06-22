package com.secret.bussiness.dewu;

import com.secret.bussiness.dewu.service.ISearchService;
import com.secret.bussiness.dewu.service.impl.SearchServiceImpl;

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
        searchService.getProductList("CQ7740-001");
        /*SearchJsonRootBean searchResult = searchService.getSearchResult("阿里巴巴");
        Gson searchJson = new GsonBuilder().setPrettyPrinting().create();;

        System.out.println(searchJson.toJson(searchResult));


        IBaseInfoService baseInfoService = new BaseInfoServiceImpl();
        BaseInfoJsonRootBean baseInfoResult = baseInfoService.getBaseInfoResult("1698375");

        Gson baseInoJson = new GsonBuilder().setPrettyPrinting().create();;
        System.out.println(baseInoJson.toJson(baseInfoResult));


        List<List> excelData= new ArrayList<List>();
        List<Object> dataList = new ArrayList<Object>();
        dataList.add(baseInfoResult.getData().getName());
        dataList.add(baseInfoResult.getData().getLegalInfo().getName());
        excelData.add(dataList);

        List<String> rowNames = new ArrayList<String>();
        rowNames.add("公司名");
        rowNames.add("法人信息");
        ExcelExportUtils excelExportUtils = new ExcelExportUtils(rowNames, excelData);
        excelExportUtils.exportData();*/
    }
}
