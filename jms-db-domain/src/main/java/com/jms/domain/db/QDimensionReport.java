package com.jms.domain.db;
// Generated 2016-3-9 10:11:30 by Hibernate Tools 3.2.2.GA


import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * QDimensionReport generated by hbm2java
 */
@Entity
@Table(name="q_dimension_report"
    ,catalog="jms4"
)
public class QDimensionReport  implements java.io.Serializable {


     private Long idReport;
     private Company company;
     private Users usersByCheckBy;
     private Users usersByInspector;
     private SPoMaterial SPoMaterial;
     private QConclusionDic QConclusionDic;
     private String reportNo;
     private Date date;
     private String lotNo;
     private Long totalQty;
     private Long inspQty;
     private String remark;
     private String boxNo;
     private Set<QReportRecord> QReportRecords = new HashSet<QReportRecord>(0);
     private Set<QAtt> QAtts = new HashSet<QAtt>(0);

    public QDimensionReport() {
    }

	
    public QDimensionReport(Long idReport) {
        this.idReport = idReport;
    }
    public QDimensionReport(Long idReport, Company company, Users usersByCheckBy, Users usersByInspector, SPoMaterial SPoMaterial, QConclusionDic QConclusionDic, String reportNo, Date date, String lotNo, Long totalQty, Long inspQty, String remark, String boxNo, Set<QReportRecord> QReportRecords, Set<QAtt> QAtts) {
       this.idReport = idReport;
       this.company = company;
       this.usersByCheckBy = usersByCheckBy;
       this.usersByInspector = usersByInspector;
       this.SPoMaterial = SPoMaterial;
       this.QConclusionDic = QConclusionDic;
       this.reportNo = reportNo;
       this.date = date;
       this.lotNo = lotNo;
       this.totalQty = totalQty;
       this.inspQty = inspQty;
       this.remark = remark;
       this.boxNo = boxNo;
       this.QReportRecords = QReportRecords;
       this.QAtts = QAtts;
    }
   
     @Id 
    
    @Column(name="id_report", unique=true, nullable=false)
    public Long getIdReport() {
        return this.idReport;
    }
    
    public void setIdReport(Long idReport) {
        this.idReport = idReport;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_company")
    public Company getCompany() {
        return this.company;
    }
    
    public void setCompany(Company company) {
        this.company = company;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="check_by")
    public Users getUsersByCheckBy() {
        return this.usersByCheckBy;
    }
    
    public void setUsersByCheckBy(Users usersByCheckBy) {
        this.usersByCheckBy = usersByCheckBy;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="inspector")
    public Users getUsersByInspector() {
        return this.usersByInspector;
    }
    
    public void setUsersByInspector(Users usersByInspector) {
        this.usersByInspector = usersByInspector;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_po_material")
    public SPoMaterial getSPoMaterial() {
        return this.SPoMaterial;
    }
    
    public void setSPoMaterial(SPoMaterial SPoMaterial) {
        this.SPoMaterial = SPoMaterial;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_conclusion")
    public QConclusionDic getQConclusionDic() {
        return this.QConclusionDic;
    }
    
    public void setQConclusionDic(QConclusionDic QConclusionDic) {
        this.QConclusionDic = QConclusionDic;
    }
    
    @Column(name="report_no", length=20)
    public String getReportNo() {
        return this.reportNo;
    }
    
    public void setReportNo(String reportNo) {
        this.reportNo = reportNo;
    }
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="date", length=19)
    public Date getDate() {
        return this.date;
    }
    
    public void setDate(Date date) {
        this.date = date;
    }
    
    @Column(name="lot_no", length=20)
    public String getLotNo() {
        return this.lotNo;
    }
    
    public void setLotNo(String lotNo) {
        this.lotNo = lotNo;
    }
    
    @Column(name="total_qty")
    public Long getTotalQty() {
        return this.totalQty;
    }
    
    public void setTotalQty(Long totalQty) {
        this.totalQty = totalQty;
    }
    
    @Column(name="insp_qty")
    public Long getInspQty() {
        return this.inspQty;
    }
    
    public void setInspQty(Long inspQty) {
        this.inspQty = inspQty;
    }
    
    @Column(name="remark", length=1024)
    public String getRemark() {
        return this.remark;
    }
    
    public void setRemark(String remark) {
        this.remark = remark;
    }
    
    @Column(name="box_no", length=20)
    public String getBoxNo() {
        return this.boxNo;
    }
    
    public void setBoxNo(String boxNo) {
        this.boxNo = boxNo;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="QDimensionReport")
    public Set<QReportRecord> getQReportRecords() {
        return this.QReportRecords;
    }
    
    public void setQReportRecords(Set<QReportRecord> QReportRecords) {
        this.QReportRecords = QReportRecords;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="QDimensionReport")
    public Set<QAtt> getQAtts() {
        return this.QAtts;
    }
    
    public void setQAtts(Set<QAtt> QAtts) {
        this.QAtts = QAtts;
    }




}


