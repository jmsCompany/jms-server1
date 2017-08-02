package com.jms.domain.db;
// Generated 2016-3-31 16:15:17 by Hibernate Tools 3.2.2.GA


import java.math.BigDecimal;
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

/**
 * SMaterial generated by hbm2java
 */
@Entity
@Table(name="s_material"
)
public class SMaterial  implements java.io.Serializable {


     private Long idMaterial;
     private Company company;
     private SMaterialCategory SMaterialCategory;
     private SMaterialTypeDic SMaterialTypeDic;
     private SStatusDic SStatusDic;
     private SUnitDic SUnitDicByUnitPur;
     private SUnitDic SUnitDicByUnitInf;
     private FCostCenter FCostCenter;
     private String pno;
     private String rev;
     private String des;
     private Long moq;
     private String brand;
     private String remark;
     private String autoRemark;
     private Long weight;
     private BigDecimal cost;
     private Long mpq;
     private Long safetyInv;
     
     private String lvl;
     private Float calc;
     

     
     
     private Set<SSo> SSos = new HashSet<SSo>(0);
     private Set<QNcr> QNcrs = new HashSet<QNcr>(0);
     private Set<PRoutine> PRoutines = new HashSet<PRoutine>(0);
     private Set<SMaterialPic> SMaterialPics = new HashSet<SMaterialPic>(0);
     private Set<QNcp> QNcps = new HashSet<QNcp>(0);
     private Set<MSparePart> MSpareParts = new HashSet<MSparePart>(0);
     private Set<PBom> PBoms = new HashSet<PBom>(0);
     private Set<QCheckList> QCheckLists = new HashSet<QCheckList>(0);
     private Set<SMaterialAttachment> SMaterialAttachments = new HashSet<SMaterialAttachment>(0);
     private Set<SMtfMaterial> SMtfMaterials = new HashSet<SMtfMaterial>(0);
     private Set<MHistoryPart> MHistoryParts = new HashSet<MHistoryPart>(0);
     private Set<SPoMaterial> SPoMaterials = new HashSet<SPoMaterial>(0);
     private Set<SInventory> SInventories = new HashSet<SInventory>(0);
     
     private Long emailSended;
     
     
     private Long checkCycle;
     private Long cycleUnit;
  


    public SMaterial() {
    }

    public SMaterial(Company company, SMaterialCategory SMaterialCategory, SMaterialTypeDic SMaterialTypeDic, SStatusDic SStatusDic, SUnitDic SUnitDicByUnitPur, SUnitDic SUnitDicByUnitInf, FCostCenter FCostCenter, String pno, String rev, String des, Long moq, String brand, String remark, String autoRemark, Long weight, BigDecimal cost, Long mpq, Long safetyInv, Set<SSo> SSos, Set<QNcr> QNcrs, Set<PRoutine> PRoutines, Set<SMaterialPic> SMaterialPics, Set<QNcp> QNcps, Set<MSparePart> MSpareParts, Set<PBom> PBoms, Set<QCheckList> QCheckLists, Set<SMaterialAttachment> SMaterialAttachments, Set<SMtfMaterial> SMtfMaterials, Set<MHistoryPart> MHistoryParts, Set<SPoMaterial> SPoMaterials, Set<SInventory> SInventories) {
       this.company = company;
       this.SMaterialCategory = SMaterialCategory;
       this.SMaterialTypeDic = SMaterialTypeDic;
       this.SStatusDic = SStatusDic;
       this.SUnitDicByUnitPur = SUnitDicByUnitPur;
       this.SUnitDicByUnitInf = SUnitDicByUnitInf;
       this.FCostCenter = FCostCenter;
       this.pno = pno;
       this.rev = rev;
       this.des = des;
       this.moq = moq;
       this.brand = brand;
       this.remark = remark;
       this.autoRemark = autoRemark;
       this.weight = weight;
       this.cost = cost;
       this.mpq = mpq;
       this.safetyInv = safetyInv;
       this.SSos = SSos;
       this.QNcrs = QNcrs;
       this.PRoutines = PRoutines;
       this.SMaterialPics = SMaterialPics;
       this.QNcps = QNcps;
       this.MSpareParts = MSpareParts;
       this.PBoms = PBoms;
       this.QCheckLists = QCheckLists;
       this.SMaterialAttachments = SMaterialAttachments;
       this.SMtfMaterials = SMtfMaterials;
       this.MHistoryParts = MHistoryParts;
       this.SPoMaterials = SPoMaterials;
       this.SInventories = SInventories;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)
    
    @Column(name="id_material", unique=true, nullable=false)
    public Long getIdMaterial() {
        return this.idMaterial;
    }
    
    public void setIdMaterial(Long idMaterial) {
        this.idMaterial = idMaterial;
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
    @JoinColumn(name="category")
    public SMaterialCategory getSMaterialCategory() {
        return this.SMaterialCategory;
    }
    
    public void setSMaterialCategory(SMaterialCategory SMaterialCategory) {
        this.SMaterialCategory = SMaterialCategory;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="type")
    public SMaterialTypeDic getSMaterialTypeDic() {
        return this.SMaterialTypeDic;
    }
    
    public void setSMaterialTypeDic(SMaterialTypeDic SMaterialTypeDic) {
        this.SMaterialTypeDic = SMaterialTypeDic;
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
    @JoinColumn(name="unit_pur")
    public SUnitDic getSUnitDicByUnitPur() {
        return this.SUnitDicByUnitPur;
    }
    
    public void setSUnitDicByUnitPur(SUnitDic SUnitDicByUnitPur) {
        this.SUnitDicByUnitPur = SUnitDicByUnitPur;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="unit_inf")
    public SUnitDic getSUnitDicByUnitInf() {
        return this.SUnitDicByUnitInf;
    }
    
    public void setSUnitDicByUnitInf(SUnitDic SUnitDicByUnitInf) {
        this.SUnitDicByUnitInf = SUnitDicByUnitInf;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_cost_center")
    public FCostCenter getFCostCenter() {
        return this.FCostCenter;
    }
    
    public void setFCostCenter(FCostCenter FCostCenter) {
        this.FCostCenter = FCostCenter;
    }
    
    @Column(name="pno", length=64)
    public String getPno() {
        return this.pno;
    }
    
    public void setPno(String pno) {
        this.pno = pno;
    }
    
    @Column(name="rev", length=64)
    public String getRev() {
        return this.rev;
    }
    
    public void setRev(String rev) {
        this.rev = rev;
    }
    
    @Column(name="des", length=200)
    public String getDes() {
        return this.des;
    }
    
    public void setDes(String des) {
        this.des = des;
    }
    
    @Column(name="moq")
    public Long getMoq() {
        return this.moq;
    }
    
    public void setMoq(Long moq) {
        this.moq = moq;
    }
    
    @Column(name="brand", length=64)
    public String getBrand() {
        return this.brand;
    }
    
    public void setBrand(String brand) {
        this.brand = brand;
    }
    
    @Column(name="remark", length=1024)
    public String getRemark() {
        return this.remark;
    }
    
    public void setRemark(String remark) {
        this.remark = remark;
    }
    
    @Column(name="auto_remark", length=8000)
    public String getAutoRemark() {
        return this.autoRemark;
    }
    
    public void setAutoRemark(String autoRemark) {
        this.autoRemark = autoRemark;
    }
    
    @Column(name="weight", precision=10, scale=0)
    public Long getWeight() {
        return this.weight;
    }
    
    public void setWeight(Long weight) {
        this.weight = weight;
    }
    
    @Column(name="cost", scale=4)
    public BigDecimal getCost() {
        return this.cost;
    }
    
    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }
    
    @Column(name="mpq", precision=10, scale=0)
    public Long getMpq() {
        return this.mpq;
    }
    
    public void setMpq(Long mpq) {
        this.mpq = mpq;
    }
    
    @Column(name="safety_inv")
    public Long getSafetyInv() {
        return this.safetyInv;
    }
    
    public void setSafetyInv(Long safetyInv) {
        this.safetyInv = safetyInv;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="SMaterial")
    public Set<SSo> getSSos() {
        return this.SSos;
    }
    
    public void setSSos(Set<SSo> SSos) {
        this.SSos = SSos;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="SMaterial")
    public Set<QNcr> getQNcrs() {
        return this.QNcrs;
    }
    
    public void setQNcrs(Set<QNcr> QNcrs) {
        this.QNcrs = QNcrs;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="SMaterial")
    public Set<PRoutine> getPRoutines() {
        return this.PRoutines;
    }
    
    public void setPRoutines(Set<PRoutine> PRoutines) {
        this.PRoutines = PRoutines;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="SMaterial")
    public Set<SMaterialPic> getSMaterialPics() {
        return this.SMaterialPics;
    }
    
    public void setSMaterialPics(Set<SMaterialPic> SMaterialPics) {
        this.SMaterialPics = SMaterialPics;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="SMaterial")
    public Set<QNcp> getQNcps() {
        return this.QNcps;
    }
    
    public void setQNcps(Set<QNcp> QNcps) {
        this.QNcps = QNcps;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="SMaterial")
    public Set<MSparePart> getMSpareParts() {
        return this.MSpareParts;
    }
    
    public void setMSpareParts(Set<MSparePart> MSpareParts) {
        this.MSpareParts = MSpareParts;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="SMaterial")
    public Set<PBom> getPBoms() {
        return this.PBoms;
    }
    
    public void setPBoms(Set<PBom> PBoms) {
        this.PBoms = PBoms;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="SMaterial")
    public Set<QCheckList> getQCheckLists() {
        return this.QCheckLists;
    }
    
    public void setQCheckLists(Set<QCheckList> QCheckLists) {
        this.QCheckLists = QCheckLists;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="SMaterial")
    public Set<SMaterialAttachment> getSMaterialAttachments() {
        return this.SMaterialAttachments;
    }
    
    public void setSMaterialAttachments(Set<SMaterialAttachment> SMaterialAttachments) {
        this.SMaterialAttachments = SMaterialAttachments;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="SMaterial")
    public Set<SMtfMaterial> getSMtfMaterials() {
        return this.SMtfMaterials;
    }
    
    public void setSMtfMaterials(Set<SMtfMaterial> SMtfMaterials) {
        this.SMtfMaterials = SMtfMaterials;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="SMaterial")
    public Set<MHistoryPart> getMHistoryParts() {
        return this.MHistoryParts;
    }
    
    public void setMHistoryParts(Set<MHistoryPart> MHistoryParts) {
        this.MHistoryParts = MHistoryParts;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="SMaterial")
    public Set<SPoMaterial> getSPoMaterials() {
        return this.SPoMaterials;
    }
    
    public void setSPoMaterials(Set<SPoMaterial> SPoMaterials) {
        this.SPoMaterials = SPoMaterials;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="SMaterial")
    public Set<SInventory> getSInventories() {
        return this.SInventories;
    }
    
    public void setSInventories(Set<SInventory> SInventories) {
        this.SInventories = SInventories;
    }

    @Column(name="check_cycle")
	public Long getCheckCycle() {
		return checkCycle;
	}

	public void setCheckCycle(Long checkCycle) {
		this.checkCycle = checkCycle;
	}

    @Column(name="cycle_unit")
	public Long getCycleUnit() {
		return cycleUnit;
	}

	public void setCycleUnit(Long cycleUnit) {
		this.cycleUnit = cycleUnit;
	}
	
	@Column(name="email_sended")
	public Long getEmailSended() {
		return emailSended;
	}

	public void setEmailSended(Long emailSended) {
		this.emailSended = emailSended;
	}

	@Column(name="lvl", length=45)
	public String getLvl() {
		return lvl;
	}

	public void setLvl(String lvl) {
		this.lvl = lvl;
	}

	@Column(name="calc")
	public Float getCalc() {
		return calc;
	}

	public void setCalc(Float calc) {
		this.calc = calc;
	}


}


