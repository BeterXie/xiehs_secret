package com.secret.bussiness.dewu;

import com.secret.bussiness.dewu.service.ISearchService;
import com.secret.bussiness.dewu.service.impl.SearchServiceImpl;
import com.secret.bussiness.dewu.utils.ExcelExportUtils;
import com.secret.bussiness.dewu.utils.HttpUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.apache.poi.ss.usermodel.*;
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
 * @package com.secret.bussiness.dewu
 * @date 2021/6/18  16:35
 */
public class DewuProductSearch1 {

    protected static Logger logger = LoggerFactory.getLogger(DewuProductSearch1.class);

    public static void main(String[] args) {
        try {
            List<Map<String, String>> maps = ExcelExportUtils.redExcel("G:\\dewu\\成都20210629.xlsx");
            getExcel(maps);
            logger.info("查询结束");
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
            int count = 0;
            List<Map<String, String>> ipList = new ArrayList<>();
            /*for(int i=0;i<1;i++){
                ipList.add(getIp());
                Thread.sleep(1000);
            }
            int ipCount = count  %  5;*/
            for(Map<String, String> map : maps){
                    count++;
                    Thread.sleep(5000);
                    String name = map.get("品牌名称");
                    String productCode = map.get("款号");
                    String vipPrice = map.get("唯品价");
                    vipPrice = vipPrice.substring(0,vipPrice.lastIndexOf("."));
                    String size = map.get("尺码");
                    logger.info("第"+count+"条商品，当前查询到："+productCode);
                    JSONObject json = searchService.getProductList(productCode,null);
                    if(json == null || !json.getJSONObject("data").containsKey("productList")){
                        logger.info("查询次数过多，需要校验验证码");
                        json = searchService.getProductList(productCode,getIp());
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
        String path = "G:\\dewu\\成都查询20210629.xlsx";
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
            Cell cell = row1.createCell(i);
            CellStyle cs = workbook.createCellStyle();
            if(!dewuMap.get("productCode").equals(dewuMap.get("articleNumber"))){
                if(i== 1 || i == 2) {
                    cs.setFillPattern(FillPatternType.SOLID_FOREGROUND);//设置前景填充样式
                    cs.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
                }
            }
            if(i >= 4){
                cell.setCellType(CellType.NUMERIC);
                DataFormat df = workbook.createDataFormat(); // 此处设置数据格式
                cs.setDataFormat(df.getFormat("0.00_ "));// 最关键的是'_ '，最后有个空格别忘了，空格是必须的
                cell.setCellValue(Double.parseDouble(messageList.get(i)));
            }else{
                cell.setCellValue(messageList.get(i));
            }
            cell.setCellStyle(cs);
        }
        FileOutputStream outputStream = new FileOutputStream(xlsxFile);
        outputStream.flush();
        workbook.write(outputStream);
        outputStream.close();
    }

    public static Map<String, String> getIp() throws Exception{
        String ipUrl="http://piping.mogumiao.com/proxy/api/get_ip_al?appKey=dc295551a29b4c28be1acdcee726d0ca&count=1&expiryDate=0&format=1&newLine=2";
        HttpResponse httpResponse = HttpUtils.get(ipUrl);
        String s = EntityUtils.toString(httpResponse.getEntity());
        JSONObject content = JSONObject.fromObject(s);
        JSONObject msg = content.getJSONArray("msg").getJSONObject(0);
        Map<String,String> map = new HashMap<>();
        map.put("ip",msg.getString("ip"));
        map.put("port",msg.getString("port"));
        return map;
    }
}
