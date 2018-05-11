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
public class ValuationOfFundVo2 {
    private Long id;
    private String kmdm;// 科目代码
    private String kmmc;// 科目名称
    private String sl;// 数量
    private String dwcb;// 单位成本
    private String cb;// 成本
    private String cbzjz;// 成本占净值%
    private String sj;// 市价
    private String sz;// 市值
    private String szzjz;// 市值占净值%
    private String gzzc;// 估值增值
    private String tpxx;// 停牌信息
    private String qyxx;// 权益信息
    /**  
     * @return the qyxx  
     */
    public String getQyxx() {
        return qyxx;
    }
    /**  
     * @param qyxx the qyxx to set  
     */
    public void setQyxx(String qyxx) {
        this.qyxx = qyxx;
    }
    @ExcelCell(index = 12)
    private String gzrq;// 估值日期
    @ExcelCell(index = 13)
    private String cp;// 估值表分类（产品）
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
    public String getSl() {
      return sl;
    }
    /**  
     * @param sl the sl to set  
     */
    public void setSl(String sl) {
      this.sl = sl;
    }
    /**  
     * @return the dwcb  
     */
    public String getDwcb() {
      return dwcb;
    }
    /**  
     * @param dwcb the dwcb to set  
     */
    public void setDwcb(String dwcb) {
      this.dwcb = dwcb;
    }
    /**  
     * @return the cb  
     */
    public String getCb() {
      return cb;
    }
    /**  
     * @param cb the cb to set  
     */
    public void setCb(String cb) {
      this.cb = cb;
    }
    /**  
     * @return the cbzjz  
     */
    public String getCbzjz() {
      return cbzjz;
    }
    /**  
     * @param cbzjz the cbzjz to set  
     */
    public void setCbzjz(String cbzjz) {
      this.cbzjz = cbzjz;
    }
    /**  
     * @return the sj  
     */
    public String getSj() {
      return sj;
    }
    /**  
     * @param sj the sj to set  
     */
    public void setSj(String sj) {
      this.sj = sj;
    }
    /**  
     * @return the sz  
     */
    public String getSz() {
      return sz;
    }
    /**  
     * @param sz the sz to set  
     */
    public void setSz(String sz) {
      this.sz = sz;
    }
    /**  
     * @return the szzjz  
     */
    public String getSzzjz() {
      return szzjz;
    }
    /**  
     * @param szzjz the szzjz to set  
     */
    public void setSzzjz(String szzjz) {
      this.szzjz = szzjz;
    }
    /**  
     * @return the gzzc  
     */
    public String getGzzc() {
      return gzzc;
    }
    /**  
     * @param gzzc the gzzc to set  
     */
    public void setGzzc(String gzzc) {
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
    public String getCp() {
      return cp;
    }
    /**  
     * @param type the type to set  
     */
    public void setCp(String type) {
      this.cp = type;
    }
   

}

