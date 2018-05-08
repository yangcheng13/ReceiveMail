/**
 * @author SargerasWang
 */
package com.apex;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import com.apex.util.DbUtils;
import com.apex.util.ExcelExtUtil;
import com.apex.util.ExcelLogs;
import com.apex.util.ExcelUtil;
import com.apex.util.vo.ValuationOfFundVo;

/**
 * 测试导入Excel 97/2003
 */
public class TestImportExcel {

    @SuppressWarnings("rawtypes")
    @Test
    public void importXls() throws SQLException, FileNotFoundException {

//        File f = new File("D:/mailTest/玉泉831号-2018-05-02.xls");
        File f = new File("D:/mailTest/2018年05月03日383天津国投-渤海汇金-2017年第54号(其他)委托资产资产估值表.xls");
        InputStream inputStream = new FileInputStream(f);
        ExcelLogs logs = new ExcelLogs();
        Collection<Map> importExcel = ExcelUtil.importExcel(Map.class,
                inputStream, "yyyy/MM/ddHH:mm:ss", logs, 0);
        for (Map m : importExcel) {
            System.out.println(m);
        }

        // Collection<ValuationOfFundVo> importExcel2 = ExcelUtil.importExcel(
        // ValuationOfFundVo.class, inputStream, "yyyy/MM/dd HH:mm:ss", logs, 0);
        // List<ValuationOfFundVo> lst = new ArrayList<ValuationOfFundVo>();
        // for (ValuationOfFundVo vo : importExcel2) {
        // if(vo.getCb() == null ||!StringUtils.isNumeric(vo.getKmdm()))
        // {
        // continue;
        // }
        // vo.setGzrq("2018-05-02");
        // vo.setType("玉泉831号");
        // lst.add(vo);
        // }
        // DbUtils.saveBatch(lst);

    }
    
    @Test
    public void importXls2() throws SQLException, FileNotFoundException {

//        File f = new File("D:/mailTest/玉泉831号-2018-05-02.xls");
        File f = new File("D:/mailTest/2018年05月03日383天津国投-渤海汇金-2017年第54号(其他)委托资产资产估值表.xls");
        InputStream inputStream = new FileInputStream(f);
        Map<Integer,String> titleMap = new TreeMap<>();
        Collection<Map> importExcel = ExcelExtUtil.importExcel(inputStream,3, titleMap);
        System.out.println("表头："+titleMap);
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        for (Map m : importExcel) {
            System.out.println(m);
        }
    }

}
