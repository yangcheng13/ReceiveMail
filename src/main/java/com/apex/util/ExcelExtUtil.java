package com.apex.util;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**  
 * @desc: ReceiveMail  
 * @author: yangcheng  
 * @createTime: 2018年5月8日 下午5:36:22    
 * @version: v1.0    
 */    
public class ExcelExtUtil {

    private static Logger LG = LoggerFactory.getLogger(ExcelExtUtil.class);

    /**  
     * 获取单元格值
     * @author: yangcheng 
     * @createTime: 2018年5月8日 下午5:36:03    
     * @param cell
     * @return Object  
     */  
    private static Object getCellValue(Cell cell) {
        if (cell == null || (cell.getCellTypeEnum() == CellType.STRING
                && StringUtils.isBlank(cell.getStringCellValue()))) {
            return null;
        }
        CellType cellType = cell.getCellTypeEnum();
        if (cellType == CellType.BLANK)
            return null;
        else if (cellType == CellType.BOOLEAN)
            return cell.getBooleanCellValue();
        else if (cellType == CellType.ERROR)
            return cell.getErrorCellValue();
        else if (cellType == CellType.FORMULA) {
            try {
                if (HSSFDateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue();
                } else {
                    return cell.getNumericCellValue();
                }
            } catch (IllegalStateException e) {
                return cell.getRichStringCellValue();
            }
        } else if (cellType == CellType.NUMERIC) {
            if (DateUtil.isCellDateFormatted(cell)) {
                return cell.getDateCellValue();
            } else {
                return cell.getNumericCellValue();
            }
        } else if (cellType == CellType.STRING)
            return cell.getStringCellValue();
        else
            return null;
    }

    /**  
     * Excel的数据封装成List<Map>
     * @author: yangcheng 
     * @createTime: 2018年5月8日 下午5:35:11    
     * @param inputStream
     * @param titleRowNum
     * @param titleMap
     * @param cp
     * @return List<Map<Integer,String>>  
     */  
    public static List<Map<Integer,String>> importExcel(InputStream inputStream,Integer titleRowNum, Map<Integer,String> titleMap,String cp) {
        Workbook workBook;
        try {
            workBook = WorkbookFactory.create(inputStream);
        } catch (Exception e) {
            LG.error("load excel file error", e);
            return null;
        }
        List<Map<Integer,String>> list = new ArrayList<Map<Integer,String>>();
        Sheet sheet = workBook.getSheetAt(0);
        String time = sheet.getRow(2).getCell(0).getStringCellValue();
        Iterator<Row> rowIterator = sheet.rowIterator();
        try {
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                if (row.getRowNum() == titleRowNum) {

                    // 解析map用的key,就是excel标题行
                    Iterator<Cell> cellIterator = row.cellIterator();
                    Integer index = 0;
                    while (cellIterator.hasNext()) {
                        String value = cellIterator.next().getStringCellValue();
                        titleMap.put(index,value);
                        index++;
                    }
                }
                if (row.getRowNum() <= titleRowNum) {
                    continue;
                }
                // 整行都空，就跳过
                boolean allRowIsNull = true;
                Iterator<Cell> cellIterator = row.cellIterator();
                while (cellIterator.hasNext()) {
                    Object cellValue = getCellValue(cellIterator.next());
                    if (cellValue != null) {
                        allRowIsNull = false;
                        break;
                    }
                }
                if (allRowIsNull) {
                    LG.warn("Excel row " + row.getRowNum()
                            + " all row value is null!");
                    continue;
                }
                Map<Integer, String> map = new TreeMap<Integer, String>();
                for (Integer k : titleMap.keySet()) {
                    Cell cell = row.getCell(k);
                    // 判空
                    if (cell == null) {
                        map.put(k, null);
                    } else {
                        cell.setCellType(CellType.STRING);
                        String value = cell.getStringCellValue();
                        map.put(k, value);
                    }
                }
                map.put(12, time.split("：")[1].replace("年", "-").replace("月", "-").replace("日", ""));
                map.put(13, cp);
                list.add(map);
            }
        } catch (Exception e) {
            LG.error("parse excel file error", e);
        }
        return list;
    }

}
