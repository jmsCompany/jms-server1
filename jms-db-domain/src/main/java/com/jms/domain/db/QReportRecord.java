package com.jms.domain.db;
// Generated 2016-3-9 10:11:30 by Hibernate Tools 3.2.2.GA


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * QReportRecord generated by hbm2java
 */
@Entity
@Table(name="q_report_record"
    ,catalog="jms4"
)
public class QReportRecord  implements java.io.Serializable {


     private Long idReportRecord;
     private QCheckList QCheckList;
     private QDimensionReport QDimensionReport;
     private Long orderBy;
     private String r01;
     private String r02;
     private String r03;
     private String r04;
     private String r05;
     private String r06;
     private String r07;
     private String r08;
     private String r09;
     private String r10;
     private String r11;
     private String r12;
     private String finalResult;

    public QReportRecord() {
    }

	
    public QReportRecord(Long idReportRecord) {
        this.idReportRecord = idReportRecord;
    }
    public QReportRecord(Long idReportRecord, QCheckList QCheckList, QDimensionReport QDimensionReport, Long orderBy, String r01, String r02, String r03, String r04, String r05, String r06, String r07, String r08, String r09, String r10, String r11, String r12, String finalResult) {
       this.idReportRecord = idReportRecord;
       this.QCheckList = QCheckList;
       this.QDimensionReport = QDimensionReport;
       this.orderBy = orderBy;
       this.r01 = r01;
       this.r02 = r02;
       this.r03 = r03;
       this.r04 = r04;
       this.r05 = r05;
       this.r06 = r06;
       this.r07 = r07;
       this.r08 = r08;
       this.r09 = r09;
       this.r10 = r10;
       this.r11 = r11;
       this.r12 = r12;
       this.finalResult = finalResult;
    }
   
     @Id 
    
    @Column(name="id_report_record", unique=true, nullable=false)
    public Long getIdReportRecord() {
        return this.idReportRecord;
    }
    
    public void setIdReportRecord(Long idReportRecord) {
        this.idReportRecord = idReportRecord;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_check_list")
    public QCheckList getQCheckList() {
        return this.QCheckList;
    }
    
    public void setQCheckList(QCheckList QCheckList) {
        this.QCheckList = QCheckList;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_report")
    public QDimensionReport getQDimensionReport() {
        return this.QDimensionReport;
    }
    
    public void setQDimensionReport(QDimensionReport QDimensionReport) {
        this.QDimensionReport = QDimensionReport;
    }
    
    @Column(name="order_by")
    public Long getOrderBy() {
        return this.orderBy;
    }
    
    public void setOrderBy(Long orderBy) {
        this.orderBy = orderBy;
    }
    
    @Column(name="r01", length=20)
    public String getR01() {
        return this.r01;
    }
    
    public void setR01(String r01) {
        this.r01 = r01;
    }
    
    @Column(name="r02", length=20)
    public String getR02() {
        return this.r02;
    }
    
    public void setR02(String r02) {
        this.r02 = r02;
    }
    
    @Column(name="r03", length=20)
    public String getR03() {
        return this.r03;
    }
    
    public void setR03(String r03) {
        this.r03 = r03;
    }
    
    @Column(name="r04", length=20)
    public String getR04() {
        return this.r04;
    }
    
    public void setR04(String r04) {
        this.r04 = r04;
    }
    
    @Column(name="r05", length=20)
    public String getR05() {
        return this.r05;
    }
    
    public void setR05(String r05) {
        this.r05 = r05;
    }
    
    @Column(name="r06", length=20)
    public String getR06() {
        return this.r06;
    }
    
    public void setR06(String r06) {
        this.r06 = r06;
    }
    
    @Column(name="r07", length=20)
    public String getR07() {
        return this.r07;
    }
    
    public void setR07(String r07) {
        this.r07 = r07;
    }
    
    @Column(name="r08", length=20)
    public String getR08() {
        return this.r08;
    }
    
    public void setR08(String r08) {
        this.r08 = r08;
    }
    
    @Column(name="r09", length=20)
    public String getR09() {
        return this.r09;
    }
    
    public void setR09(String r09) {
        this.r09 = r09;
    }
    
    @Column(name="r10", length=20)
    public String getR10() {
        return this.r10;
    }
    
    public void setR10(String r10) {
        this.r10 = r10;
    }
    
    @Column(name="r11", length=20)
    public String getR11() {
        return this.r11;
    }
    
    public void setR11(String r11) {
        this.r11 = r11;
    }
    
    @Column(name="r12", length=20)
    public String getR12() {
        return this.r12;
    }
    
    public void setR12(String r12) {
        this.r12 = r12;
    }
    
    @Column(name="final_result", length=20)
    public String getFinalResult() {
        return this.finalResult;
    }
    
    public void setFinalResult(String finalResult) {
        this.finalResult = finalResult;
    }




}


