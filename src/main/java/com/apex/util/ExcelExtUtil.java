package com.apex.util;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.ComparatorUtils;
import org.apache.commons.collections.comparators.ComparableComparator;
import org.apache.commons.collections.comparators.ComparatorChain;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * The <code>ExcelUtil</code> 与 {@link ExcelCell}搭配使用
 *
 * @author sargeras.wang
 * @version 1.0, Created at 2013年9月14日
 */
public class ExcelExtUtil {

    private static Logger LG = LoggerFactory.getLogger(ExcelExtUtil.class);

    /**
     * 获取单元格值
     *
     * @param cell
     * @return
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
     * 把Excel的数据封装成voList
     *
     * @param clazz vo的Class
     * @param inputStream excel输入流
     * @param pattern 如果有时间数据，设定输入格式。默认为"yyy-MM-dd"
     * @param logs 错误log集合
     * @param arrayCount 如果vo中有数组类型,那就按照index顺序,把数组应该有几个值写上.
     * @return voList
     * @throws RuntimeException
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
                map.put(12, time.split("：")[1]);
                map.put(13, cp);
                list.add(map);
            }
        } catch (Exception e) {
            LG.error("parse excel file error", e);
        }
        return list;
    }



}
