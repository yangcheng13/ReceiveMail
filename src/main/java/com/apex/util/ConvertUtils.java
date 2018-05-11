/*
 * Copyright @ 2018 com.apexsoft ReceiveMail 上午11:45:45 All right reserved.
 */
package com.apex.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import com.apex.util.vo.DwjzVo;
import com.apex.util.vo.ValuationOfFundVo;
import com.apex.util.vo.ValuationOfFundVo2;

/**
 * @desc: ReceiveMail
 * @author: yangcheng
 * @createTime: 2018年5月7日 上午11:45:45
 * @version: v1.0
 */
public class ConvertUtils {

    public static List<ValuationOfFundVo> convertMapToFundVoLst(
            List<Map<Integer, String>> excelValueMaps,
            Map<Integer, String> titleMap) {
        List<ValuationOfFundVo> lst = new ArrayList<ValuationOfFundVo>();
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

    public static List<ValuationOfFundVo2> convertMapToFundVo2Lst(
            List<Map<Integer, String>> excelValueMaps,
            Map<Integer, String> titleMap) {
        List<ValuationOfFundVo2> lst = new ArrayList<ValuationOfFundVo2>();
        for (Map<Integer, String> m : excelValueMaps) {
            if (StringUtils.isNumeric((CharSequence) m.get(0))) {
                continue;
            }
            ValuationOfFundVo2 vo = new ValuationOfFundVo2();
            if(m.get(0)==null){
                continue;
            }
            vo.setKmdm(m.get(0));
            vo.setKmmc(m.get(1));
            vo.setSl(m.get(2));
            vo.setDwcb(m.get(3));
            vo.setCb(m.get(4));
            vo.setCbzjz(m.get(5));
            vo.setSj(m.get(6));
            vo.setSz(m.get(7));
            vo.setSzzjz(m.get(8));
            vo.setGzzc(m.get(9));
            vo.setTpxx(m.get(10));
            vo.setQyxx(m.get(11));
            vo.setGzrq(m.get(12));
            vo.setCp(m.get(13));
            lst.add(vo);
        }
        return lst;

    }

    public static DwjzVo convertMapToVo(
            List<Map<Integer, String>> excelValueMaps,
            Map<String, Object> keyMap) {
        DwjzVo vo = new DwjzVo();
        vo.setCp((String) keyMap.get("cp"));
        for (Map<Integer, String> m : excelValueMaps) {
            if (StringUtils.isNumeric((CharSequence) m.get(0))) {
                continue;
            }
            if (StringUtils.isNotEmpty(m.get(0))
                    && m.get(0).equals(keyMap.get("v1mc"))) {
                vo.setV1(Double.parseDouble(m.get(1)));
                vo.setGzrq(m.get(12));
            }
            if (StringUtils.isNotEmpty(m.get(0))
                    && m.get(0).equals(keyMap.get("v2mc"))) {
                vo.setV2(Double.parseDouble(m.get(1)));
            }
            if (vo.getV1() != null && vo.getV2() != null) {
                break;
            }

        }
        return vo;
    }


    public static String convertDecimal(String doubleStr, int n) {
        BigDecimal d1 = new BigDecimal(doubleStr);
        BigDecimal d2 = new BigDecimal(Integer.toString(1));
        // 四舍五入,保留n位小数
        return d1.divide(d2, n, BigDecimal.ROUND_HALF_UP).toString();
    }

}

