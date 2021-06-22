package com.secret.bussiness.dewu.utils;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @Author: chenyanfeng
 * @Date: 2018-11-23
 * @Time: 下午4:21
 */
public class ExcelExportUtils {

    /**
     * 导出表格的列名
     */
    private List<String> rowName;

    /**
     * 对象数组的List集合
     */
    private List<List> dataList = new ArrayList<List>();



    /**
     * 实例化导出类
     * @param rowName 导出表格的列名数组
     * @param dataList 对象数组的List集合
     */
    public ExcelExportUtils( List rowName, List<List>  dataList){
        this.rowName=rowName;
        this.dataList=dataList;
    }

    /**
     * 导出数据
     * @throws Exception
     */
    public void exportData() throws Exception{
        // 创建一个excel对象
        HSSFWorkbook workbook =new HSSFWorkbook();
        // 创建表格
        HSSFSheet sheet =workbook.createSheet("搜索结果");


        // sheet样式定义
        // 头样式
        HSSFCellStyle columnTopStyle = this.getColumnTopStyle(workbook,16);
        // 标题样式
        HSSFCellStyle columnStyle = this.getColumnStyle(workbook,14);
        // 单元格样式
        HSSFCellStyle style = this.getStyle(workbook,11);

        // 表格列的长度
        int columnNum = rowName.size();
        HSSFRow rowRowName = sheet.createRow(0);
        // 在第二行创建行
        HSSFCellStyle cells =workbook.createCellStyle();
        cells.setBottomBorderColor(HSSFColor.BLACK.index);
        rowRowName.setRowStyle(cells);

        // 循环 将列名放进去
        for (int i = 0; i < columnNum; i++) {
            HSSFCell cellRowName = rowRowName.createCell(i);
            // 单元格类型
            cellRowName.setCellType(HSSFCell.CELL_TYPE_STRING);
            // 得到列的值
            HSSFRichTextString text = new HSSFRichTextString(rowName.get(i));
            // 设置列的值
            cellRowName.setCellValue(text);
            // 样式
            cellRowName.setCellStyle(columnStyle);
        }

        // 将查询到的数据设置到对应的单元格中
        for (int i = 0; i < dataList.size(); i++) {
            //遍历每个对象
            List<Object> obj = dataList.get(i);
            //创建所需的行数
            HSSFRow row = sheet.createRow(i+1);
            for (int j = 0; j < obj.size(); j++) {
                //设置单元格的数据类型
                HSSFCell  cell = null;

                cell = row.createCell(j,HSSFCell.CELL_TYPE_STRING);
                if(!"".equals(obj.get(j)) && obj.get(j) != null){
                    //设置单元格的值
                    cell.setCellValue(obj.get(j).toString());
                }else{
                    cell.setCellValue("");
                }

                // 样式
                cell.setCellStyle(style);
            }
        }

        // 让列宽随着导出的列长自动适应，但是对中文支持不是很好  也可能在linux（无图形环境的操作系统）下报错，报错再说
        for (int i = 0; i < columnNum; i++) {
            sheet.autoSizeColumn(i);
            //适当再宽点
            sheet.setColumnWidth(i, sheet.getColumnWidth(i)+888);
        }

        if(workbook !=null){
            File file = new File("查询结果.xls");
            OutputStream out = new FileOutputStream(file);
            try {
                workbook.write(out);
                out.flush();
                workbook.close();
            } catch (Exception e) {
                throw e;
            } finally {
                if (null != out) {
                    out.close();
                }
            }
        }
    }

    public HSSFCellStyle getColumnTopStyle(HSSFWorkbook workbook,int fontSize) {
        // 设置字体
        HSSFFont font = workbook.createFont();
        //设置字体大小
        font.setFontHeightInPoints((short)fontSize);
        //字体加粗
        //font.setBoldweight(HSSFFont.FONT_ARIAL);
        //设置字体名字
        font.setFontName("宋体");
        //设置样式;
        HSSFCellStyle style = workbook.createCellStyle();
        //在样式用应用设置的字体;
        style.setFont(font);
        //设置自动换行;
        style.setWrapText(false);
        //设置水平对齐的样式为居中对齐;
        style.setAlignment(HorizontalAlignment.CENTER);
        //设置垂直对齐的样式为居中对齐;
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        return style;
    }

    public HSSFCellStyle getColumnStyle(HSSFWorkbook workbook,int fontSize) {
        // 设置字体
        HSSFFont font = workbook.createFont();
        //设置字体大小
        font.setFontHeightInPoints((short)fontSize);
        //字体加粗
        font.setBold(true);
        //设置字体名字
        font.setFontName("宋体");
        //设置样式;
        HSSFCellStyle style = workbook.createCellStyle();
        //设置底边框;
        style.setBorderBottom(BorderStyle.THIN);
        //设置底边框颜色;
        style.setBottomBorderColor(HSSFColor.BLACK.index);
        //设置左边框;
        style.setBorderLeft(BorderStyle.THIN);
        //设置左边框颜色;
        style.setLeftBorderColor(HSSFColor.BLACK.index);
        //设置右边框;
        style.setBorderRight(BorderStyle.THIN);
        //设置右边框颜色;
        style.setRightBorderColor(HSSFColor.BLACK.index);
        //设置顶边框;
        style.setBorderTop(BorderStyle.THIN);
        //设置顶边框颜色;
        style.setTopBorderColor(HSSFColor.BLACK.index);
        //在样式用应用设置的字体;
        style.setFont(font);
        //设置自动换行;
        style.setWrapText(false);
        //设置水平对齐的样式为居中对齐;
        style.setAlignment(HorizontalAlignment.CENTER);
        //设置垂直对齐的样式为居中对齐;
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        return style;
    }

    public HSSFCellStyle getStyle(HSSFWorkbook workbook,int fontSize) {
        //设置字体
        HSSFFont font = workbook.createFont();
        //设置字体大小
        font.setFontHeightInPoints((short)fontSize);
        //设置字体名字
        font.setFontName("宋体");
        //设置样式;
        HSSFCellStyle style = workbook.createCellStyle();
        //设置底边框;
        style.setBorderBottom(BorderStyle.THIN);
        //设置底边框颜色;
        style.setBottomBorderColor(HSSFColor.BLACK.index);
        //设置左边框;
        style.setBorderLeft(BorderStyle.THIN);
        //设置左边框颜色;
        style.setLeftBorderColor(HSSFColor.BLACK.index);
        //设置右边框;
        style.setBorderRight(BorderStyle.THIN);
        //设置右边框颜色;
        style.setRightBorderColor(HSSFColor.BLACK.index);
        //设置顶边框;
        style.setBorderTop(BorderStyle.THIN);
        //设置顶边框颜色;
        style.setTopBorderColor(HSSFColor.BLACK.index);
        //在样式用应用设置的字体;
        style.setFont(font);
        //设置自动换行;
        style.setWrapText(false);
        //设置水平对齐的样式为居中对齐;
        style.setAlignment(HorizontalAlignment.CENTER);
        //设置垂直对齐的样式为居中对齐;
        style.setVerticalAlignment(VerticalAlignment.CENTER);

        return style;
    }

    public static void main(String[] args) {
        try {
            List<Map<String, String>> maps = redExcel("G:\\新建文件夹\\WeChat Files\\wxid_xixumdf91cpw21\\FileStorage\\File\\2021-06\\新店体用折扣款.xlsx");
            maps.forEach(System.out::println);
        }catch (Exception e){
            System.out.println(e);
        }
    }

    /**
     * 读取excel内容
     * <p>
     * 用户模式下：
     * 弊端：对于少量的数据可以，单数对于大量的数据，会造成内存占据过大，有时候会造成内存溢出
     * 建议修改成事件模式
     */
    public static List<Map<String, String>> redExcel(String filePath) throws Exception {
        File file = new File(filePath);
        if (!file.exists()){
            throw new Exception("文件不存在!");
        }
        InputStream in = new FileInputStream(file);

        // 读取整个Excel
        XSSFWorkbook sheets = new XSSFWorkbook(in);
        // 获取第一个表单Sheet
        XSSFSheet sheetAt = sheets.getSheetAt(0);
        ArrayList<Map<String, String>> list = new ArrayList<>();

        //默认第一行为标题行，i = 0
        XSSFRow titleRow = sheetAt.getRow(0);
        // 循环获取每一行数据
        for (int i = 1; i < sheetAt.getPhysicalNumberOfRows(); i++) {
            XSSFRow row = sheetAt.getRow(i);
            LinkedHashMap<String, String> map = new LinkedHashMap<>();
            // 读取每一格内容
            for (int index = 0; index < row.getPhysicalNumberOfCells(); index++) {
                XSSFCell titleCell = titleRow.getCell(index);
                XSSFCell cell = row.getCell(index);
                // cell.setCellType(XSSFCell.CELL_TYPE_STRING); 过期，使用下面替换
                cell.setCellType(CellType.STRING);
                if (cell.getStringCellValue().equals("")) {
                    continue;
                }
                map.put(getString(titleCell), getString(cell));
            }
            if (map.isEmpty()) {
                continue;
            }
            list.add(map);
        }
        return list;
    }

    /**
     * 把单元格的内容转为字符串
     *
     * @param xssfCell 单元格
     * @return String
     */
    public static String getString(XSSFCell xssfCell) {
        if (xssfCell == null) {
            return "";
        }
        if (xssfCell.getCellTypeEnum() == CellType.NUMERIC) {
            return String.valueOf(xssfCell.getNumericCellValue());
        } else if (xssfCell.getCellTypeEnum() == CellType.BOOLEAN) {
            return String.valueOf(xssfCell.getBooleanCellValue());
        } else {
            return xssfCell.getStringCellValue();
        }
    }


}
