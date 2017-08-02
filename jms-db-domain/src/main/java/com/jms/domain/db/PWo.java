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
 * PWo generated by hbm2java
 */
@Entity
@Table(name="p_wo"
)
public class PWo  implements java.io.Serializable {


     private Long idWo;
     private SSo SSo;
     private Users users;
     private PStatusDic PStatusDic;
     private String woNo;
     private Date creationTime;
     private Long qty;
     private Set<PWoBom> PWoBoms = new HashSet<PWoBom>(0);
     private Set<QFaiInFinal> QFaiInFinals = new HashSet<QFaiInFinal>(0);
     private Set<PSPp> PSPps = new HashSet<PSPp>(0);
     private Set<PWoRoute> PWoRoutes = new HashSet<PWoRoute>(0);
     private Set<PCPp> PCPps = new HashSet<PCPp>(0);
     
     
     private Date st;
     private Date ft;
     
     private Date actSt;
     private Date actFt;
     private Long actQty;
     
     private Long typeId;
     private String orgWo;

    public PWo() {
    }

   
     @Id @GeneratedValue(strategy=IDENTITY)
    
    @Column(name="id_wo", unique=true, nullable=false)
    public Long getIdWo() {
        return this.idWo;
    }
    
    public void setIdWo(Long idWo) {
        this.idWo = idWo;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_so")
    public SSo getSSo() {
        return this.SSo;
    }
    
    public void setSSo(SSo SSo) {
        this.SSo = SSo;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="creator")
    public Users getUsers() {
        return this.users;
    }
    
    public void setUsers(Users users) {
        this.users = users;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_pstatus")
    public PStatusDic getPStatusDic() {
        return this.PStatusDic;
    }
    
    public void setPStatusDic(PStatusDic PStatusDic) {
        this.PStatusDic = PStatusDic;
    }
    
    @Column(name="wo_no", length=20)
    public String getWoNo() {
        return this.woNo;
    }
    
    public void setWoNo(String woNo) {
        this.woNo = woNo;
    }
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="creation_time", length=19)
    public Date getCreationTime() {
        return this.creationTime;
    }
    
    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }
    
    @Column(name="qty")
    public Long getQty() {
        return this.qty;
    }
    
    public void setQty(Long qty) {
        this.qty = qty;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="PWo")
    public Set<PWoBom> getPWoBoms() {
        return this.PWoBoms;
    }
    
    public void setPWoBoms(Set<PWoBom> PWoBoms) {
        this.PWoBoms = PWoBoms;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="PWo")
    public Set<QFaiInFinal> getQFaiInFinals() {
        return this.QFaiInFinals;
    }
    
    public void setQFaiInFinals(Set<QFaiInFinal> QFaiInFinals) {
        this.QFaiInFinals = QFaiInFinals;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="PWo")
    public Set<PSPp> getPSPps() {
        return this.PSPps;
    }
    
    public void setPSPps(Set<PSPp> PSPps) {
        this.PSPps = PSPps;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="PWo")
    public Set<PWoRoute> getPWoRoutes() {
        return this.PWoRoutes;
    }
    
    public void setPWoRoutes(Set<PWoRoute> PWoRoutes) {
        this.PWoRoutes = PWoRoutes;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="PWo")
    public Set<PCPp> getPCPps() {
        return this.PCPps;
    }
    
    public void setPCPps(Set<PCPp> PCPps) {
        this.PCPps = PCPps;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="act_st", length=19)
	public Date getActSt() {
		return actSt;
	}

	public void setActSt(Date actSt) {
		this.actSt = actSt;
	}

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="act_ft", length=19)
	public Date getActFt() {
		return actFt;
	}

	public void setActFt(Date actFt) {
		this.actFt = actFt;
	}
    @Column(name="act_qty")
	public Long getActQty() {
		return this.actQty;
	}

	public void setActQty(Long actQty) {
		this.actQty = actQty;
	}

	 @Column(name="type_id")
	public Long getTypeId() {
		return typeId;
	}


	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}

    @Column(name="org_wo", length=64)
	public String getOrgWo() {
		return orgWo;
	}


	public void setOrgWo(String orgWo) {
		this.orgWo = orgWo;
	}


    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="st", length=19)
	public Date getSt() {
		return st;
	}


	public void setSt(Date st) {
		this.st = st;
	}


	  @Temporal(TemporalType.TIMESTAMP)
	    @Column(name="ft", length=19)
	public Date getFt() {
		return ft;
	}


	public void setFt(Date ft) {
		this.ft = ft;
	}




}


