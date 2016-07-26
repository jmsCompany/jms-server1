package com.jms.domain.db;
// Generated 2016-3-31 16:15:17 by Hibernate Tools 3.2.2.GA


import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * PUnplannedStops generated by hbm2java
 */
@Entity
@Table(name="p_unplanned_stops"
    ,catalog="jms5"
)
public class PUnplannedStops  implements java.io.Serializable {


     private Long idUnplannedStops;
     private PSubCode PSubCode;
     private PStopsCode PStopsCode;
     private PStatusDic PStatusDic;
     private Long unplannedStopsNo;
     private Long idMachine;
     private String opDes;
     private Date unplannedSt;
     private String reason;
     private String reaction;
     private Date opSt;
     private Date eqSt;
     private Date eqFt;
     private Date opFt;
     
     private Long idCompany;
     private Long idCpp;

    public PUnplannedStops() {
    }

    public PUnplannedStops(PSubCode PSubCode, PStopsCode PStopsCode, PStatusDic PStatusDic, Long unplannedStopsNo, String opDes, Date unplannedSt, String reason, String reaction, Date opSt, Date eqSt, Date eqFt, Date opFt) {
       this.PSubCode = PSubCode;
       this.PStopsCode = PStopsCode;
       this.PStatusDic = PStatusDic;
       this.unplannedStopsNo = unplannedStopsNo;
       this.opDes = opDes;
       this.unplannedSt = unplannedSt;
       this.reason = reason;
       this.reaction = reaction;
       this.opSt = opSt;
       this.eqSt = eqSt;
       this.eqFt = eqFt;
       this.opFt = opFt;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)
    
    @Column(name="id_unplanned_stops", unique=true, nullable=false)
    public Long getIdUnplannedStops() {
        return this.idUnplannedStops;
    }
    
    public void setIdUnplannedStops(Long idUnplannedStops) {
        this.idUnplannedStops = idUnplannedStops;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_sub_code")
    public PSubCode getPSubCode() {
        return this.PSubCode;
    }
    
    public void setPSubCode(PSubCode PSubCode) {
        this.PSubCode = PSubCode;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_stops_code")
    public PStopsCode getPStopsCode() {
        return this.PStopsCode;
    }
    
    public void setPStopsCode(PStopsCode PStopsCode) {
        this.PStopsCode = PStopsCode;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_pstatus")
    public PStatusDic getPStatusDic() {
        return this.PStatusDic;
    }
    
    public void setPStatusDic(PStatusDic PStatusDic) {
        this.PStatusDic = PStatusDic;
    }
    
    @Column(name="unplanned_stops_no")
    public Long getUnplannedStopsNo() {
        return this.unplannedStopsNo;
    }
    
    public void setUnplannedStopsNo(Long unplannedStopsNo) {
        this.unplannedStopsNo = unplannedStopsNo;
    }
    
    @Column(name="op_des", length=128)
    public String getOpDes() {
        return this.opDes;
    }
    
    public void setOpDes(String opDes) {
        this.opDes = opDes;
    }
    @Temporal(TemporalType.DATE)
    @Column(name="unplanned_st", length=10)
    public Date getUnplannedSt() {
        return this.unplannedSt;
    }
    
    public void setUnplannedSt(Date unplannedSt) {
        this.unplannedSt = unplannedSt;
    }
    
    @Column(name="reason", length=1024)
    public String getReason() {
        return this.reason;
    }
    
    public void setReason(String reason) {
        this.reason = reason;
    }
    
    @Column(name="reaction", length=1024)
    public String getReaction() {
        return this.reaction;
    }
    
    public void setReaction(String reaction) {
        this.reaction = reaction;
    }
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="op_st", length=19)
    public Date getOpSt() {
        return this.opSt;
    }
    
    public void setOpSt(Date opSt) {
        this.opSt = opSt;
    }
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="eq_st", length=19)
    public Date getEqSt() {
        return this.eqSt;
    }
    
    public void setEqSt(Date eqSt) {
        this.eqSt = eqSt;
    }
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="eq_ft", length=19)
    public Date getEqFt() {
        return this.eqFt;
    }
    
    public void setEqFt(Date eqFt) {
        this.eqFt = eqFt;
    }
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="op_ft", length=19)
    public Date getOpFt() {
        return this.opFt;
    }
    
    public void setOpFt(Date opFt) {
        this.opFt = opFt;
    }
    
    @Column(name="id_machine")
	public Long getIdMachine() {
		return idMachine;
	}

	public void setIdMachine(Long idMachine) {
		this.idMachine = idMachine;
	}

    @Column(name="id_company")
	public Long getIdCompany() {
		return idCompany;
	}

	public void setIdCompany(Long idCompany) {
		this.idCompany = idCompany;
	}
	@Column(name="id_cpp")
	public Long getIdCpp() {
		return idCpp;
	}

	public void setIdCpp(Long idCpp) {
		this.idCpp = idCpp;
	}



}


