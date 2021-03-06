package com.jms.repositories.p;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.jms.domain.db.PWo;
import com.jms.domain.db.SMaterial;


@Repository
public interface PWoRepository extends JpaRepository<PWo, Long>{

    @Query("select p from PWo p where p.SSo.company.idCompany=?1 order by p.idWo,p.PStatusDic.idPstatus")
    public List<PWo> getByCompanyId(Long companyId);

    @Query("select s.SSo.SMaterial from PWo s where s.idWo=?1")
	public SMaterial findByWoId(Long woId);
    
    
    @Query("select p from PWo p where p.SSo.company.idCompany=?1 and p.PStatusDic.idPstatus=12 order by p.actSt")
    public List<PWo> getActiveWosByCompanyId(Long companyId);
    
    
    @Query("select p from PWo p where p.SSo.company.idCompany=?1 and (p.woNo like ?2 or p.SSo.SMaterial.pno like ?2 or  p.SSo.SMaterial.des like ?2) order by p.PStatusDic.idPstatus")
    public List<PWo> getByCompanyIdAndQuery(Long companyId,String q);
    
    
    @Query("select p from PWo p where p.SSo.company.idCompany=?1 and p.SSo.SMaterial.idMaterial=?2 and p.PStatusDic.idPstatus=12")
    public List<PWo> findOpenWoByCompanyIdAndProductId(Long companyId,Long productId);
    
    
}
