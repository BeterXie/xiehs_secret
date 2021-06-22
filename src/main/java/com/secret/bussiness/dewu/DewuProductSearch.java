package com.secret.bussiness.dewu;

import com.secret.bussiness.dewu.service.ISearchService;
import com.secret.bussiness.dewu.service.impl.SearchServiceImpl;
import com.secret.bussiness.dewu.utils.ExcelExportUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xiehs
 * @package cn.xiaoyanol.crawler
 * @date 2021/6/18  16:35
 */
public class DewuProductSearch {

    protected static Logger logger = LoggerFactory.getLogger(DewuProductSearch.class);

    public static void main(String[] args) {
        try {
            List<Map<String, String>> maps = ExcelExportUtils.redExcel("C:\\Users\\xianbeilin1.xlsx");
            getExcel(maps);
        }catch (Exception e){
            System.out.println(e);
        }
        /*Map<String, String> map  =  new HashMap<>();
        map.put("品牌名称","白粉紫");
        map.put("款号","FY3466");
        map.put("唯品价","229");
        map.put("尺码","43");
        List<Map<String, String>> maps = new ArrayList<>();
        maps.add(map);
        try {
            getExcel(maps);
        }catch (Exception e){
            System.out.println(e);
        }*/
    }

    public  static  void  getExcel(List<Map<String, String>> maps) throws Exception {

            if (!CollectionUtils.isNotEmpty(maps)) {
                System.out.println("数据为空");
                return;
            }
            ISearchService searchService = new SearchServiceImpl();
            //List<Map<String, String>> dewuMaps = new ArrayList<>(16);
            int count = 489;
            for(Map<String, String> map : maps){
                count++;
                Thread.sleep(5 * 1000);
                String name = map.get("品牌名称");
                String productCode = map.get("款号");
                String vipPrice = map.get("唯品价");
                String size = map.get("尺码");
                logger.info("第"+count+"条商品，当前查询到："+productCode);
                JSONObject json = searchService.getProductList(productCode);
                if(!json.getJSONObject("data").containsKey("productList")){
                    logger.info("查询次数过多，需要校验验证码");
                    break;
                }
                JSONArray jsonArray = json.getJSONObject("data").getJSONArray("productList");
                if(jsonArray.size() == 0){
                    continue;
                }
                JSONObject dewuObject = jsonArray.getJSONObject(0);
                long price = dewuObject.getLong("price") / 100;
                Long profit = price-Long.valueOf(vipPrice) - 33 - 12;
                Long num = Long.parseLong(dewuObject.getString("soldNum"));
                String articleNumber = dewuObject.getString("articleNumber");
                Map<String, String> dewuProduct = new  HashMap<>();
                dewuProduct.put("name",name);//品牌
                dewuProduct.put("size",size);//鞋码
                dewuProduct.put("articleNumber",articleNumber);//得物货号
                dewuProduct.put("productCode",productCode);//唯品会货号
                dewuProduct.put("vipPrice",vipPrice);//唯品价格
                dewuProduct.put("packingFee","33");//包装费
                dewuProduct.put("courierFee","12");//快递费
                dewuProduct.put("transferFee",String.valueOf(price * 0.06));//转账费
                dewuProduct.put("dewuPrice",String.valueOf(price));
                dewuProduct.put("soldNum",dewuObject.getString("soldNum"));
                dewuProduct.put("profit",String.valueOf(price-Long.valueOf(vipPrice) - 33 - 12));//利润
                outPutExcel(dewuProduct,count);
                //dewuMaps.add(dewuProduct);
            }

        }

    public  static  void outPutExcel(Map<String, String> dewuMap,int rowNum) throws Exception{
        String path = "C:\\查询结果"+20210621+".xlsx";
        File xlsxFile = new File(path);

        //如果文件不存在，创建文件
        if (!xlsxFile.exists()) {
            //创建一个工作簿
            XSSFWorkbook workbook = new XSSFWorkbook();
            //创建一个工作表
            XSSFSheet sheet = workbook.createSheet("sheet1");

            //初始化第一行信息头
            Row row = sheet.createRow(0);
            row.createCell(0).setCellValue("品牌");
            row.createCell(1).setCellValue("得物货号");
            row.createCell(2).setCellValue("唯品会货号");
            row.createCell(3).setCellValue("尺寸");
            row.createCell(4).setCellValue("唯品价");
            row.createCell(5).setCellValue("得物价格");
            row.createCell(6).setCellValue("得物销量");
            row.createCell(7).setCellValue("包装费");
            row.createCell(8).setCellValue("转账费");
            row.createCell(9).setCellValue("快递费");
            row.createCell(10).setCellValue("利润");
            FileOutputStream outputStream = new FileOutputStream(xlsxFile);
            workbook.write(outputStream);
            outputStream.close();
        }
        //打开工作簿
        FileInputStream fileInputStream = new FileInputStream(xlsxFile);
        Workbook workbook = new XSSFWorkbook(fileInputStream);
        //获取工作表
        Sheet sheet = workbook.getSheet("sheet1");
        //提取信息
        List<String> messageList = new ArrayList<String>();
        messageList.add(dewuMap.get("name"));
        messageList.add(dewuMap.get("articleNumber"));
        messageList.add(dewuMap.get("productCode"));
        messageList.add(dewuMap.get("size"));
        messageList.add(dewuMap.get("vipPrice"));
        messageList.add(dewuMap.get("dewuPrice"));
        messageList.add(dewuMap.get("soldNum"));
        messageList.add(dewuMap.get("packingFee"));
        messageList.add(dewuMap.get("transferFee"));
        messageList.add(dewuMap.get("courierFee"));
        messageList.add(dewuMap.get("profit"));
        Row row1 = sheet.createRow(rowNum);
        for (int i = 0; i < messageList.size(); i++) {
            row1.createCell(i).setCellValue(messageList.get(i));
        }
        FileOutputStream outputStream = new FileOutputStream(xlsxFile);
        outputStream.flush();
        workbook.write(outputStream);
        outputStream.close();
    }
}
