package com.jms.domain.db;
// Generated 2016-3-31 16:15:17 by Hibernate Tools 3.2.2.GA


import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * SMtf generated by hbm2java
 */
@Entity
@Table(name="s_mtf"
)
public class SMtf  implements java.io.Serializable {


     private Long idMt;
     private SMtfTypeDic SMtfTypeDic;
     private Company company;
     private SStk SStkByToStk;
     private SStatusDic SStatusDic;
     private SMtfResidual SMtfResidual;
     private Users usersByEmpMt;
     private Users usersByRecMt;
     private SStk SStkByFromStk;
     private String mtNo;
     private Date dateMt;
     private Date creationTime;
     
     private Long idWo;
     
     private Long idSmtfC;
     
     
     private Long idToCompany;
     private Long idCustomerCompany;
     
     private String coOrderNo;
     
     private Long cRev;
     private Long tRev;
     
     
     private Long idR;
     
     private Set<SMtfMaterial> SMtfMaterials = new HashSet<SMtfMaterial>(0);

    public SMtf() {
    }

    public SMtf(SMtfTypeDic SMtfTypeDic, Company company, SStk SStkByToStk, SStatusDic SStatusDic, SMtfResidual SMtfResidual, Users usersByEmpMt, Users usersByRecMt, SStk SStkByFromStk, String mtNo, Date dateMt, Date creationTime, Set<SMtfMaterial> SMtfMaterials) {
       this.SMtfTypeDic = SMtfTypeDic;
       this.company = company;
       this.SStkByToStk = SStkByToStk;
       this.SStatusDic = SStatusDic;
       this.SMtfResidual = SMtfResidual;
       this.usersByEmpMt = usersByEmpMt;
       this.usersByRecMt = usersByRecMt;
       this.SStkByFromStk = SStkByFromStk;
       this.mtNo = mtNo;
       this.dateMt = dateMt;
       this.creationTime = creationTime;
       this.SMtfMaterials = SMtfMaterials;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)
    
    @Column(name="id_mt", unique=true, nullable=false)
    public Long getIdMt() {
        return this.idMt;
    }
    
    public void setIdMt(Long idMt) {
        this.idMt = idMt;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_mtf_type")
    public SMtfTypeDic getSMtfTypeDic() {
        return this.SMtfTypeDic;
    }
    
    public void setSMtfTypeDic(SMtfTypeDic SMtfTypeDic) {
        this.SMtfTypeDic = SMtfTypeDic;
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
    @JoinColumn(name="to_stk")
    public SStk getSStkByToStk() {
        return this.SStkByToStk;
    }
    
    public void setSStkByToStk(SStk SStkByToStk) {
        this.SStkByToStk = SStkByToStk;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="status")
    public SStatusDic getSStatusDic() {
        return this.SStatusDic;
    }
    
    public void setSStatusDic(SStatusDic SStatusDic) {
        this.SStatusDic = SStatusDic;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_mtf_residual")
    public SMtfResidual getSMtfResidual() {
        return this.SMtfResidual;
    }
    
    public void setSMtfResidual(SMtfResidual SMtfResidual) {
        this.SMtfResidual = SMtfResidual;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="emp_mt")
    public Users getUsersByEmpMt() {
        return this.usersByEmpMt;
    }
    
    public void setUsersByEmpMt(Users usersByEmpMt) {
        this.usersByEmpMt = usersByEmpMt;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="rec_mt")
    public Users getUsersByRecMt() {
        return this.usersByRecMt;
    }
    
    public void setUsersByRecMt(Users usersByRecMt) {
        this.usersByRecMt = usersByRecMt;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="from_stk")
    public SStk getSStkByFromStk() {
        return this.SStkByFromStk;
    }
    
    public void setSStkByFromStk(SStk SStkByFromStk) {
        this.SStkByFromStk = SStkByFromStk;
    }
    
    @Column(name="mt_no", length=20)
    public String getMtNo() {
        return this.mtNo;
    }
    
    public void setMtNo(String mtNo) {
        this.mtNo = mtNo;
    }
    @Temporal(TemporalType.DATE)
    @Column(name="date_mt", length=10)
    public Date getDateMt() {
        return this.dateMt;
    }
    
    public void setDateMt(Date dateMt) {
        this.dateMt = dateMt;
    }
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="creation_time", length=19)
    public Date getCreationTime() {
        return this.creationTime;
    }
    
    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="SMtf")
    public Set<SMtfMaterial> getSMtfMaterials() {
        return this.SMtfMaterials;
    }
    
    public void setSMtfMaterials(Set<SMtfMaterial> SMtfMaterials) {
        this.SMtfMaterials = SMtfMaterials;
    }

    @Column(name="id_wo")
	public Long getIdWo() {
		return idWo;
	}

	public void setIdWo(Long idWo) {
		this.idWo = idWo;
	}

	@Column(name="id_smtf_c")
	public Long getIdSmtfC() {
		return idSmtfC;
	}

	public void setIdSmtfC(Long idSmtfC) {
		this.idSmtfC = idSmtfC;
	}
	   @Column(name="id_to_company")
	public Long getIdToCompany() {
		return idToCompany;
	}

	public void setIdToCompany(Long idToCompany) {
		this.idToCompany = idToCompany;
	}
	   @Column(name="id_customer_company")
	public Long getIdCustomerCompany() {
		return idCustomerCompany;
	}

	public void setIdCustomerCompany(Long idCustomerCompany) {
		this.idCustomerCompany = idCustomerCompany;
	}

	@Column(name="co_order_no", length=64)
	public String getCoOrderNo() {
		return coOrderNo;
	}

	public void setCoOrderNo(String coOrderNo) {
		this.coOrderNo = coOrderNo;
	}
	   @Column(name="c_rev")
	public Long getcRev() {
		return cRev;
	}

	public void setcRev(Long cRev) {
		this.cRev = cRev;
	}

	   @Column(name="t_rev")
	public Long gettRev() {
		return tRev;
	}

	public void settRev(Long tRev) {
		this.tRev = tRev;
	}

	 @Column(name="id_r")
	public Long getIdR() {
		return idR;
	}

	public void setIdR(Long idR) {
		this.idR = idR;
	}



}


