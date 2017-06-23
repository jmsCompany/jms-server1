package com.jms.repositories.s;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.jms.domain.db.SComCom;

@Repository
public interface SComComRepository  extends JpaRepository<SComCom, Long>{
	
	//申请
	@Query("select s from SComCom s where s.idCompany1=?1 and (s.status<>2 and s.status<>5)")
	public List<SComCom> findMyApplies(Long idCompany);
	
	//审批
	@Query("select s from SComCom s where s.idCompany2=?1 and (s.status=1 or s.status=4)")
	public List<SComCom> findMyAudits(Long idCompany);
	
	
	//已同意
	@Query("select s from SComCom s where (s.idCompany2=?1 or s.idCompany1=?1) and s.status=2")
	public List<SComCom> findAgrees(Long idCompany);
	
	//已拒绝
	@Query("select s from SComCom s where (s.idCompany2=?1 or s.idCompany1=?1) and s.status=3")
	public List<SComCom> findDegrees(Long idCompany);
	
	
	//往来公司
	@Query("select s from SComCom s where (s.idCompany1=?1 or s.idCompany2=?1) and s.status=2")
	public List<SComCom> findMyCompanies(Long idCompany);
	
	
	//根据两个公司id 查SComCom
	@Query("select s from SComCom s where ((s.idCompany1=?1 and s.idCompany2=?2) or (s.idCompany2=?1 and s.idCompany1=?2)) and s.status=2")
	public SComCom findByTwoCompanyId(Long idCompany1,Long idCompany2);
	
	
}