/*  
 * Copyright @ 2018 com.apexsoft  
 * ReceiveMail 上午11:45:45  
 * All right reserved.  
 */      
package com.apex.util;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import org.apache.commons.dbutils.QueryRunner;
import com.apex.form.DataAccess;
import com.apex.util.vo.ValuationOfFundVo;

/**  
 * @desc: ReceiveMail  
 * @author: yangcheng  
 * @createTime: 2018年5月7日 上午11:45:45    
 * @version: v1.0    
 */
public class DbUtils {

    /**  
     * constructor of DbUtils.java
     */
    public DbUtils() {}
    
    public static void saveBatch(List<ValuationOfFundVo> lst) throws SQLException{
      QueryRunner qr = new QueryRunner(JdbcUtil.getDataSource());
//      QueryRunner qr = new QueryRunner(DataAccess.getDataSource());
//      qr.update("INSERT INTO entity_tzjjgz (`kmdm`, `kmmc`, `sl`, `dwcb`, `cbzjz`, `sj`, `sz`, `szzjz`, `gzzc`, `tpxx`, `gzrq`, `type`, `cb`) values(?,?,?,?,?,?,?,?,?,?,?,?,?)",
//                      new Object[] {vo.getKmdm(),vo.getKmmc(),vo.getSl(),vo.getDwcb(),vo.getCbzjz(),vo.getSj(),vo.getSz(),vo.getSzzjz(),vo.getGzzc(),vo.getTpxx(),vo.getGzrq(),vo.getType(),vo.getCb()});
      Object[][] params=new Object[lst.size()][];
      for(int i=0;i<params.length;i++){
        ValuationOfFundVo vo = lst.get(i);
          params[i]=new Object[] {vo.getKmdm(),vo.getKmmc(),vo.getSl(),vo.getDwcb(),vo.getCbzjz(),vo.getSj(),vo.getSz(),vo.getSzzjz(),vo.getGzzc(),vo.getTpxx(),vo.getGzrq(),vo.getType(),vo.getCb()};
      }
      qr.batch("INSERT INTO entity_tzjjgz (`kmdm`, `kmmc`, `sl`, `dwcb`, `cbzjz`, `sj`, `sz`, `szzjz`, `gzzc`, `tpxx`, `gzrq`, `type`, `cb`) values(?,?,?,?,?,?,?,?,?,?,?,?,?)", params);
  }

}
  
    