package com.jms.domain.db;
// Generated 2016-3-9 10:11:30 by Hibernate Tools 3.2.2.GA


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

/**
 * QNcrPic generated by hbm2java
 */
@Entity
@Table(name="q_ncr_pic"
    ,catalog="jms4"
)
public class QNcrPic  implements java.io.Serializable {


     private Long idNcrPic;
     private Users users;
     private String orgFilename;
     private String filename;
     private String des;
     private Set<QNcr> QNcrsForPic1 = new HashSet<QNcr>(0);
     private Set<QNcr> QNcrsForPic2 = new HashSet<QNcr>(0);

    public QNcrPic() {
    }

	
    public QNcrPic(Long idNcrPic) {
        this.idNcrPic = idNcrPic;
    }
    public QNcrPic(Long idNcrPic, Users users, String orgFilename, String filename, String des, Set<QNcr> QNcrsForPic1, Set<QNcr> QNcrsForPic2) {
       this.idNcrPic = idNcrPic;
       this.users = users;
       this.orgFilename = orgFilename;
       this.filename = filename;
       this.des = des;
       this.QNcrsForPic1 = QNcrsForPic1;
       this.QNcrsForPic2 = QNcrsForPic2;
    }
   
     @Id 
    
    @Column(name="id_ncr_pic", unique=true, nullable=false)
    public Long getIdNcrPic() {
        return this.idNcrPic;
    }
    
    public void setIdNcrPic(Long idNcrPic) {
        this.idNcrPic = idNcrPic;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="upload_by")
    public Users getUsers() {
        return this.users;
    }
    
    public void setUsers(Users users) {
        this.users = users;
    }
    
    @Column(name="org_filename", length=256)
    public String getOrgFilename() {
        return this.orgFilename;
    }
    
    public void setOrgFilename(String orgFilename) {
        this.orgFilename = orgFilename;
    }
    
    @Column(name="filename", length=256)
    public String getFilename() {
        return this.filename;
    }
    
    public void setFilename(String filename) {
        this.filename = filename;
    }
    
    @Column(name="des", length=1024)
    public String getDes() {
        return this.des;
    }
    
    public void setDes(String des) {
        this.des = des;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="QNcrPicByPic1")
    public Set<QNcr> getQNcrsForPic1() {
        return this.QNcrsForPic1;
    }
    
    public void setQNcrsForPic1(Set<QNcr> QNcrsForPic1) {
        this.QNcrsForPic1 = QNcrsForPic1;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="QNcrPicByPic2")
    public Set<QNcr> getQNcrsForPic2() {
        return this.QNcrsForPic2;
    }
    
    public void setQNcrsForPic2(Set<QNcr> QNcrsForPic2) {
        this.QNcrsForPic2 = QNcrsForPic2;
    }




}


