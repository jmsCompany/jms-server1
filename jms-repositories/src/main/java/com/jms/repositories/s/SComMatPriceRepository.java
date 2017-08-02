package com.jms.repositories.s;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.jms.domain.db.SComMatPrice;


@Repository
public interface SComMatPriceRepository  extends JpaRepository<SComMatPrice, Long>{
	
	public List<SComMatPrice> findByIdMat(Long idMat);
	

	public SComMatPrice findByIdMatAndIsPrim(Long idMat, Long isPrim);
	
	public List<SComMatPrice>  findByIdCompanyAndIdCom(Long idCompany,Long idCom);
	
	
	public List<SComMatPrice>  findByIdCompany(Long idCompany);
	
	public List<SComMatPrice>  findByIdCompanyAndIdComAndIdMat(Long idCompany,Long idCom, Long idMat);

	
	
}