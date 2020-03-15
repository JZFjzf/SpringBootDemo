package com.choimroc.mybatisplusdemo.tool;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ExcelUtils {
    private final static String excel2003 =".xls";
    private final static String excel2007 =".xlsx";

    /**
     * @param in
     * @param fileName
     * 处理上传的excel文件
     *
     * */
    public static List<List<Object>> getBankListByExcel(InputStream in, String fileName) throws Exception{
        List<List<Object>> list;
        //创建Excel工作薄
        Workbook work = getWorkbook(in,fileName);
        if(null == work){
            throw new Exception("创建Excel工作薄为空！");
        }
        Sheet sheet ;
        Row row ;
        Cell cell ;

        list = new ArrayList<>();
        for (int i = 0; i < work.getNumberOfSheets(); i++) {
            sheet = work.getSheetAt(i);
            if(sheet==null){continue;}

            for (int j = sheet.getFirstRowNum(); j <= sheet.getLastRowNum(); j++) {
                row = sheet.getRow(j);
                if(row==null||row.getFirstCellNum()==j){continue;}

                List<Object> li = new ArrayList<>();
                for (int y = row.getFirstCellNum(); y < row.getLastCellNum(); y++) {
                    cell = row.getCell(y);
                    li.add(cell);
                }
                list.add(li);
            }
        }
        work.close();
        return list;
    }
    //判断excel文件的格式
    public static Workbook getWorkbook(InputStream inStr, String fileName) throws Exception{
        Workbook wb;
        String fileType = fileName.substring(fileName.lastIndexOf("."));
        if(excel2003.equals(fileType)){
            wb = new HSSFWorkbook(inStr);
        }else if(excel2007.equals(fileType)){
            wb = new XSSFWorkbook(inStr);
        }else{
            throw new Exception("解析的文件格式有误！");
        }
        return wb;
    }
}
