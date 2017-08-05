package com.jms.repositories.s;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.jms.domain.db.SMaterial;
import com.jms.domain.db.SSo;

@Repository
public interface SSoRepository  extends JpaRepository<SSo, Long>{
	
	@Query("select s from SSo s where s.company.idCompany=?1")
	public List<SSo> findByCompanyId(Long companyId);
	
	
	
	@Query("select s from SSo s where s.company.idCompany=?1 and s.SMaterial.idMaterial=?2 and s.SStatusDic.id=18")
	public List<SSo> findOpenSo(Long companyId,Long materialId);
	
	
	@Query("select s from SSo s where s.company.idCompany=?1 and s.SStatusDic.id=?2")
	public List<SSo> findByCompanyIdAndStatusId(Long companyId,Long statusId);
	
	@Query("select s from SSo s where s.SCompanyCo.id=?1 and s.coOrderNo=?2 and s.SStatusDic.id=18")
	public List<SSo> findByCompanyIdAndOrderNo(Long companyId,String orderNo);
	
	
	
	@Query("select s from SSo s where s.idCompany2=?1 and s.coOrderNo=?2 and s.SStatusDic.id=18")
	public List<SSo> findByCompany2IdAndOrderNo(Long company2Id,String orderNo);
	
	@Query("select s from SSo s where s.SCompanyCo.id=?1 and s.SStatusDic.id=18")
	public List<SSo> findByCompanyCoId(Long companyCoId);
	
	@Query("select s.SMaterial from SSo s where s.idSo=?1")
	public SMaterial findBySoId(Long soId);
	
	@Query(value="SELECT distinct(co_order_no) FROM s_so where code_co=?1",nativeQuery=true)
	public List<String> findCoOrderNosByCompanyCoId(Long companyCoId);
	
	
	
	@Query(value="SELECT distinct(co_order_no) FROM s_so where id_company2=?1",nativeQuery=true)
	public List<String> findCoOrderNosByCompany2Id(Long company2Id);
	
	
	@Query("select s from SSo s where s.company.idCompany=?1 and s.codeSo =?2")
	public List<SSo> findByCompanyIdAndCodeSo(Long companyId,String codeSo);
	
	
	@Query("select s from SSo s where s.company.idCompany=?1 and s.SStatusDic.id=?2 and s.codeSo like ?3")
	public List<SSo> findByCompanyIdAndStatusIdByQ(Long companyId,Long statusId,String q);
	
	
	public List<SSo> findBySoNum(Long soNum);
	
		
}