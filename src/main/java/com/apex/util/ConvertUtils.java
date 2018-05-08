/*
 * Copyright @ 2018 com.apexsoft ReceiveMail 上午11:45:45 All right reserved.
 */
package com.apex.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import com.apex.util.vo.ValuationOfFundVo;

/**
 * @desc: ReceiveMail
 * @author: yangcheng
 * @createTime: 2018年5月7日 上午11:45:45
 * @version: v1.0
 */
public class ConvertUtils {

    public static List<ValuationOfFundVo> convertMapToVo(
            List<Map<Integer, String>> excelValueMaps,
            Map<Integer, String> titleMap) {
        List<ValuationOfFundVo> lst = new ArrayList<>();
        for (Map<Integer, String> m : excelValueMaps) {
            if (m.get(4) == null
                    || !StringUtils.isNumeric((CharSequence) m.get(0))) {
                continue;
            }
            ValuationOfFundVo vo = new ValuationOfFundVo();
            vo.setKmdm(m.get(0));
            vo.setKmmc(m.get(1));
            if (StringUtils.isNotEmpty(m.get(2))) {
                vo.setSl(Double.parseDouble(m.get(2)));
            }
            if (StringUtils.isNotEmpty(m.get(3))) {
                vo.setDwcb(Double.parseDouble(m.get(3)));
            }
            if (StringUtils.isNotEmpty(m.get(4))) {
                vo.setCb(Double.parseDouble(m.get(4)));
            }
            if (StringUtils.isNotEmpty(m.get(5))) {
                vo.setCbzjz(Double.parseDouble(m.get(5)));
            }
            if (StringUtils.isNotEmpty(m.get(6))) {
                vo.setSj(Double.parseDouble(m.get(6)));
            }
            if (StringUtils.isNotEmpty(m.get(7))) {
                vo.setSz(Double.parseDouble(m.get(7)));
            }
            if (StringUtils.isNotEmpty(m.get(8))) {
                vo.setSzzjz(Double.parseDouble(m.get(8)));
            }
            if (StringUtils.isNotEmpty(m.get(9))) {
                vo.setGzzc(Double.parseDouble(m.get(9)));
            }
            vo.setTpxx(m.get(10));
            vo.setQyxx(m.get(11));
            vo.setGzrq(m.get(12));
            vo.setCp(m.get(13));
            lst.add(vo);
        }
        return lst;

    }
    
    public static String convertDecimal(String doubleStr,int n) {
        BigDecimal d1 = new BigDecimal(doubleStr);
        BigDecimal d2 = new BigDecimal(Integer.toString(1));
        // 四舍五入,保留n位小数
        return d1.divide(d2,n,BigDecimal.ROUND_HALF_UP).toString();
    }

}

