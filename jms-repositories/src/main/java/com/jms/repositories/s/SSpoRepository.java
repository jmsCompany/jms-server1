package com.jms.repositories.s;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.jms.domain.db.SPo;


@Repository
public interface SSpoRepository  extends JpaRepository<SPo, Long>{
	
	@Query("select s from SPo s where s.company.idCompany=?1")
	public List<SPo> findByCompanyId(Long companyId);
	
	
	@Query("select s from SPo s where s.company.idCompany=?1 and s.SCompanyCo.id=?2")
	public List<SPo> findByCompanyIdAndCodeCo(Long companyId,Long codeCo);
	
	
	@Query("select s from SPo s where s.company.idCompany=?1 and s.codePo=?2 and s.SStatusDic.id=11 order by s.idPo desc")
	public List<SPo> findByCompanyIdAndCodePo(Long companyId,String codePo);
	

}