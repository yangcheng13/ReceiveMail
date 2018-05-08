/*
 * Copyright @ 2018 com.apexsoft ReceiveMail 上午11:57:49 All right reserved.
 */
package com.apex.util.vo;

import com.apex.util.ExcelCell;

/**
 * @desc: ReceiveMail
 * @author: yangcheng
 * @createTime: 2018年5月7日 上午11:57:49
 * @version: v1.0
 */
public class ValuationOfFundVo {
    private Long id;
    @ExcelCell(index = 0)
    private String kmdm;// 科目代码
    @ExcelCell(index = 1)
    private String kmmc;// 科目名称
    @ExcelCell(index = 2)
    private Double sl;// 数量
    @ExcelCell(index = 3)
    private Double dwcb;// 单位成本
    @ExcelCell(index = 4)
    private Double cb;// 成本
    @ExcelCell(index = 5)
    private Double cbzjz;// 成本占净值%
    @ExcelCell(index = 6)
    private Double sj;// 市价
    @ExcelCell(index = 7)
    private Double sz;// 市值
    @ExcelCell(index = 8)
    private Double szzjz;// 市值占净值%
    @ExcelCell(index = 9)
    private Double gzzc;// 估值增值
    @ExcelCell(index = 10)
    private String tpxx;// 停牌信息
    @ExcelCell(index = 11)
    private String gzrq;// 估值日期
    @ExcelCell(index = 12)
    private String type;// 估值表分类
    /**  
     * @return the id  
     */
    public Long getId() {
      return id;
    }
    /**  
     * @param id the id to set  
     */
    public void setId(Long id) {
      this.id = id;
    }
    /**  
     * @return the kmdm  
     */
    public String getKmdm() {
      return kmdm;
    }
    /**  
     * @param kmdm the kmdm to set  
     */
    public void setKmdm(String kmdm) {
      this.kmdm = kmdm;
    }
    /**  
     * @return the kmmc  
     */
    public String getKmmc() {
      return kmmc;
    }
    /**  
     * @param kmmc the kmmc to set  
     */
    public void setKmmc(String kmmc) {
      this.kmmc = kmmc;
    }
    /**  
     * @return the sl  
     */
    public Double getSl() {
      return sl;
    }
    /**  
     * @param sl the sl to set  
     */
    public void setSl(Double sl) {
      this.sl = sl;
    }
    /**  
     * @return the dwcb  
     */
    public Double getDwcb() {
      return dwcb;
    }
    /**  
     * @param dwcb the dwcb to set  
     */
    public void setDwcb(Double dwcb) {
      this.dwcb = dwcb;
    }
    /**  
     * @return the cb  
     */
    public Double getCb() {
      return cb;
    }
    /**  
     * @param cb the cb to set  
     */
    public void setCb(Double cb) {
      this.cb = cb;
    }
    /**  
     * @return the cbzjz  
     */
    public Double getCbzjz() {
      return cbzjz;
    }
    /**  
     * @param cbzjz the cbzjz to set  
     */
    public void setCbzjz(Double cbzjz) {
      this.cbzjz = cbzjz;
    }
    /**  
     * @return the sj  
     */
    public Double getSj() {
      return sj;
    }
    /**  
     * @param sj the sj to set  
     */
    public void setSj(Double sj) {
      this.sj = sj;
    }
    /**  
     * @return the sz  
     */
    public Double getSz() {
      return sz;
    }
    /**  
     * @param sz the sz to set  
     */
    public void setSz(Double sz) {
      this.sz = sz;
    }
    /**  
     * @return the szzjz  
     */
    public Double getSzzjz() {
      return szzjz;
    }
    /**  
     * @param szzjz the szzjz to set  
     */
    public void setSzzjz(Double szzjz) {
      this.szzjz = szzjz;
    }
    /**  
     * @return the gzzc  
     */
    public Double getGzzc() {
      return gzzc;
    }
    /**  
     * @param gzzc the gzzc to set  
     */
    public void setGzzc(Double gzzc) {
      this.gzzc = gzzc;
    }
    /**  
     * @return the tpxx  
     */
    public String getTpxx() {
      return tpxx;
    }
    /**  
     * @param tpxx the tpxx to set  
     */
    public void setTpxx(String tpxx) {
      this.tpxx = tpxx;
    }
    /**  
     * @return the gzrq  
     */
    public String getGzrq() {
      return gzrq;
    }
    /**  
     * @param gzrq the gzrq to set  
     */
    public void setGzrq(String gzrq) {
      this.gzrq = gzrq;
    }
    /**  
     * @return the type  
     */
    public String getType() {
      return type;
    }
    /**  
     * @param type the type to set  
     */
    public void setType(String type) {
      this.type = type;
    }
   

}

