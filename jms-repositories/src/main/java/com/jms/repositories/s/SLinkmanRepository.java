package com.jms.repositories.s;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.jms.domain.db.SCompanyCo;
import com.jms.domain.db.SLinkman;
import com.jms.domain.db.SLinkmanId;

@Repository
public interface SLinkmanRepository  extends JpaRepository<SLinkman, SLinkmanId>{

	@Query("select s from SLinkman s where s.SCompanyCo.id=?1")
	public List<SLinkman> getByCompanyCoId(Long companyCoId);
}