package com.jms.domain.db;
// Generated 2016-3-9 10:11:30 by Hibernate Tools 3.2.2.GA


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
 * WTaskComment generated by hbm2java
 */
@Entity
@Table(name="w_task_comment"
    ,catalog="jms4"
)
public class WTaskComment  implements java.io.Serializable {


     private Long idComment;
     private Users users;
     private WTask WTask;
     private WTaskComment WTaskComment;
     private String comment;
     private Date creationTime;
     private Date modificationTime;
     private Set<WTaskComment> WTaskComments = new HashSet<WTaskComment>(0);

    public WTaskComment() {
    }

    public WTaskComment(Users users, WTask WTask, WTaskComment WTaskComment, String comment, Date creationTime, Date modificationTime, Set<WTaskComment> WTaskComments) {
       this.users = users;
       this.WTask = WTask;
       this.WTaskComment = WTaskComment;
       this.comment = comment;
       this.creationTime = creationTime;
       this.modificationTime = modificationTime;
       this.WTaskComments = WTaskComments;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)
    
    @Column(name="id_comment", unique=true, nullable=false)
    public Long getIdComment() {
        return this.idComment;
    }
    
    public void setIdComment(Long idComment) {
        this.idComment = idComment;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_user")
    public Users getUsers() {
        return this.users;
    }
    
    public void setUsers(Users users) {
        this.users = users;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_task")
    public WTask getWTask() {
        return this.WTask;
    }
    
    public void setWTask(WTask WTask) {
        this.WTask = WTask;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="parent")
    public WTaskComment getWTaskComment() {
        return this.WTaskComment;
    }
    
    public void setWTaskComment(WTaskComment WTaskComment) {
        this.WTaskComment = WTaskComment;
    }
    
    @Column(name="comment", length=1024)
    public String getComment() {
        return this.comment;
    }
    
    public void setComment(String comment) {
        this.comment = comment;
    }
    @Temporal(TemporalType.DATE)
    @Column(name="creation_time", length=10)
    public Date getCreationTime() {
        return this.creationTime;
    }
    
    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }
    @Temporal(TemporalType.DATE)
    @Column(name="modification_time", length=10)
    public Date getModificationTime() {
        return this.modificationTime;
    }
    
    public void setModificationTime(Date modificationTime) {
        this.modificationTime = modificationTime;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="WTaskComment")
    public Set<WTaskComment> getWTaskComments() {
        return this.WTaskComments;
    }
    
    public void setWTaskComments(Set<WTaskComment> WTaskComments) {
        this.WTaskComments = WTaskComments;
    }




}


