/*  
 * Copyright @ 2018 com.apexsoft  
 * ReceiveMail 上午11:45:45  
 * All right reserved.  
 */      
package com.apex.util;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import com.apex.util.vo.DwjzVo;
import com.apex.util.vo.ValuationOfFundVo;
import com.apex.util.vo.ValuationOfFundVo2;

/**  
 * @desc: ReceiveMail  
 * @author: yangcheng  
 * @createTime: 2018年5月7日 上午11:45:45    
 * @version: v1.0    
 */
public class DbExtUtils {
    
    public static void saveFundByBatch(List<ValuationOfFundVo> lst) throws SQLException{
      QueryRunner qr = new QueryRunner(JdbcUtil.getDataSource());
//      QueryRunner qr = new QueryRunner(DataAccess.getDataSource());
      Object[][] params=new Object[lst.size()][];
      for(int i=0;i<params.length;i++){
        ValuationOfFundVo vo = lst.get(i);
          params[i]=new Object[] {vo.getKmdm(),vo.getKmmc(),vo.getSl(),vo.getDwcb(),vo.getCb(),vo.getCbzjz(),vo.getSj(),vo.getSz(),vo.getSzzjz(),vo.getGzzc(),vo.getTpxx(),vo.getQyxx(),vo.getGzrq(),vo.getCp()};
      }
      qr.batch("INSERT INTO entity_tzjjgz (`kmdm`, `kmmc`, `sl`, `dwcb`,`cb`,`cbzjz`, `sj`, `sz`, `szzjz`, `gzzc`, `tpxx`,`qyxx`, `gzrq`, `cp`) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)", params);
    }
    
    /**  
     * @author: yangcheng 
     * @createTime: 2018年5月10日 上午11:04:23    
     * @param lst2 void  
     * @throws SQLException 
     */  
    public static void saveFund2ByBatch(List<ValuationOfFundVo2> lst) throws SQLException {
        QueryRunner qr = new QueryRunner(JdbcUtil.getDataSource());
//      QueryRunner qr = new QueryRunner(DataAccess.getDataSource());
      Object[][] params=new Object[lst.size()][];
      for(int i=0;i<params.length;i++){
        ValuationOfFundVo2 vo = lst.get(i);
          params[i]=new Object[] {vo.getKmdm(),vo.getKmmc(),vo.getSl(),vo.getDwcb(),vo.getCb(),vo.getCbzjz(),vo.getSj(),vo.getSz(),vo.getSzzjz(),vo.getGzzc(),vo.getTpxx(),vo.getQyxx(),vo.getGzrq(),vo.getCp()};
      }
      qr.batch("INSERT INTO entity_tzjjgz2 (`kmdm`, `kmmc`, `sl`, `dwcb`,`cb`,`cbzjz`, `sj`, `sz`, `szzjz`, `gzzc`, `tpxx`,`qyxx`, `gzrq`, `cp`) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)", params);    
            
    }
    
    public static Map<String, Object> getKeyByCp(String cp) throws SQLException{
        QueryRunner qr = new QueryRunner(JdbcUtil.getDataSource());
//        QueryRunner qr = new QueryRunner(DataAccess.getDataSource());
        String sql = "select * from entity_tzjjgzzd where cp = ?";  
        Map<String,Object> map = qr.query(sql, new MapHandler(),cp);  
        return map;
    }
    
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static int getCountByCpAndGzrq(String cp,String gzrq) throws SQLException{
        QueryRunner qr = new QueryRunner(JdbcUtil.getDataSource());
//        QueryRunner qr = new QueryRunner(DataAccess.getDataSource());
        String sql = "select count(*) from entity_tzjjgz where cp = ? and gzrq = ?";  
        Object[] params={cp,gzrq};
        int count=(int)(long)qr.query(sql,new ScalarHandler(),params);  
        return count;
    }
    
    public static void saveDwjz(DwjzVo vo) throws SQLException{
        QueryRunner qr = new QueryRunner(JdbcUtil.getDataSource());
//        QueryRunner qr = new QueryRunner(DataAccess.getDataSource());
        qr.update("INSERT INTO entity_tzjjgzzzl (`cp`, `v1`, `v2`, `gzrq`) VALUES (?,?,?,?)",
                new Object[] {vo.getCp(),vo.getV1(),vo.getV2(),vo.getGzrq()});
    }
    
    public static void main(String[] args) {
        try {
//            System.out.println(getKeyByCp("玉泉831号"));
            System.out.println(getCountByCpAndGzrq("玉泉831号","2018-05-02"));
        } catch (SQLException e) {
            //TODO Auto-generated catch block  
             e.printStackTrace();  
                
        }
    }
}
  
    