package com.jms.repositories.s;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.jms.domain.db.SBin;


@Repository
public interface SBinRepository  extends JpaRepository<SBin, Long>{
	
	@Query("select s from SBin s where s.SStk.id=?1 order by s.SYesOrNoDic.id")
	public List<SBin> getByIdStk(Long idStk);
	
	
	@Query("select s from SBin s where s.SStk.company.idCompany=?1 and s.SStk.SStkTypeDic.idStkType=?2")
	public SBin getByCompanyIdAndStkType(Long companyId,Long stkTypeId);
	
	
	@Query("select s from SBin s where s.SStk.company.idCompany=?1 and s.SStk.SStkTypeDic.idStkType=?2 and s.SStk.stkName=?3 and s.bin=?3")
	public SBin getByCompanyIdAndStkTypeAndBinName(Long companyId,Long stkTypeId,String binName);
	
	
	@Query("select s from SBin s where s.SStk.company.idCompany=?1 and s.bin=?2")
	public SBin getByCompanyIdAndBinName(Long companyId,String binName);
	
	
}