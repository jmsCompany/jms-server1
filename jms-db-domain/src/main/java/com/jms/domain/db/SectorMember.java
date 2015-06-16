package com.jms.domain.db;
// Generated 2015-6-16 15:01:20 by Hibernate Tools 3.2.2.GA


import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * SectorMember generated by hbm2java
 */
@Entity
@Table(name="sector_member"
    ,catalog="jms"
)
public class SectorMember  implements java.io.Serializable {


     private SectorMemberId id;
     private Roles roles;
     private Users users;
     private Sector sector;
     private Integer isprimary;

    public SectorMember() {
    }

	
    public SectorMember(SectorMemberId id, Users users, Sector sector) {
        this.id = id;
        this.users = users;
        this.sector = sector;
    }
    public SectorMember(SectorMemberId id, Roles roles, Users users, Sector sector, Integer isprimary) {
       this.id = id;
       this.roles = roles;
       this.users = users;
       this.sector = sector;
       this.isprimary = isprimary;
    }
   
     @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="idSector", column=@Column(name="ID_SECTOR", nullable=false) ), 
        @AttributeOverride(name="idUser", column=@Column(name="ID_USER", nullable=false) ) } )
    public SectorMemberId getId() {
        return this.id;
    }
    
    public void setId(SectorMemberId id) {
        this.id = id;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="ID_ROLE")
    public Roles getRoles() {
        return this.roles;
    }
    
    public void setRoles(Roles roles) {
        this.roles = roles;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="ID_USER", nullable=false, insertable=false, updatable=false)
    public Users getUsers() {
        return this.users;
    }
    
    public void setUsers(Users users) {
        this.users = users;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="ID_SECTOR", nullable=false, insertable=false, updatable=false)
    public Sector getSector() {
        return this.sector;
    }
    
    public void setSector(Sector sector) {
        this.sector = sector;
    }
    
    @Column(name="ISPRIMARY")
    public Integer getIsprimary() {
        return this.isprimary;
    }
    
    public void setIsprimary(Integer isprimary) {
        this.isprimary = isprimary;
    }




}


