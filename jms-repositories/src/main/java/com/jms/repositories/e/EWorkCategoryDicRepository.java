package com.jms.repositories.e;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.jms.domain.db.EWorkCategoryDic;



@Repository
public interface EWorkCategoryDicRepository extends JpaRepository<EWorkCategoryDic, Long>{
	
	@Query("select e from EWorkCategoryDic e where e.company.idCompany=?1")
	public List<EWorkCategoryDic> getByCompanyId(Long companyId);

}
