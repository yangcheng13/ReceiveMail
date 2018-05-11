package com.apex.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import org.apache.commons.collections.CollectionUtils;
import com.apex.util.vo.ValuationOfFundVo;
import com.apex.util.vo.ValuationOfFundVo2;

public class MailService {
    @SuppressWarnings("serial")
    private static Map<String,String> cpMap = new HashMap<String,String>(){
        {
            put("玉泉831号","玉泉831号");
            put("证券投资基金估值表_中信信托灵均增强2期金融投资集合资金信托计划","中信信托灵均增强2期金融投资集合资金信托计划");
            put("天津国投-渤海汇金-2017年第54号(其他)委托资产资产估值表","渤海汇金-2017年第54号(其他)委托资产");
        }
    };
    
    public void importXlsToDb() {
        try {
            //保存邮件附件
            MailUtils.receive();
            //获取暂存附件
            List<File> fileLst = new ArrayList<File>();
            MailUtils.getFilesBySubfix(PropertyUtil.getProperty("mail.attachment.savepath"), fileLst);
            if(CollectionUtils.isEmpty(fileLst))
            {
                System.out.println("昨天没有相应产品的邮件,不做解析入库处理");
                return;
            }
            //附件解析入库
            for (File file : fileLst) {
                String fileName = file.getName();
                String cp = null;
                for (Entry<String, String> entry : cpMap.entrySet()) {
                    if(fileName.contains(entry.getKey())){
                        cp = entry.getValue();
                        break;
                    }
                }
                if(cp == null){
                    System.out.println(fileName+":未知产品的文件,不做解析入库处理");
                    continue;
                }
                InputStream inputStream = new FileInputStream(file);
                Map<Integer,String> titleMap = new TreeMap<Integer,String>();
                List<Map<Integer, String>> importExcel = ExcelExtUtil.importExcel(inputStream,3,titleMap,cp);
                System.out.println("当前文件："+fileName);
                List<ValuationOfFundVo> lst = ConvertUtils.convertMapToFundVoLst(importExcel, titleMap);
                ValuationOfFundVo vo = lst.get(0);
                int count = DbExtUtils.getCountByCpAndGzrq(vo.getCp(), vo.getGzrq());
                if (count != 0){
                    System.out.println(fileName+",该文件已经解析入库过,不做入库处理");
                    continue;  
                }
                DbExtUtils.saveFundByBatch(lst);
                List<ValuationOfFundVo2> lst2 = ConvertUtils.convertMapToFundVo2Lst(importExcel, titleMap);
                DbExtUtils.saveFund2ByBatch(lst2);
            }
            //删除暂存附件
            MailUtils.deletefile(PropertyUtil.getProperty("mail.attachment.savepath"));
            
        } catch (Exception e) {
            System.out.println("importXlsToDb Exception:" + e.getMessage());
        }
       
    }
    /**  
     * @return the cpMap  
     */ 
    public static Map<String,String> getCpMap() {
            return  cpMap; 
    }
    
    public static void main(String[] args) {
        new MailService().importXlsToDb();
    }

}
