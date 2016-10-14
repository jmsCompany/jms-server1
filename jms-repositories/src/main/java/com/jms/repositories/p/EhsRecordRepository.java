package com.jms.repositories.p;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jms.domain.db.EhsItem;
import com.jms.domain.db.EhsRecord;




@Repository
public interface EhsRecordRepository extends JpaRepository<EhsRecord, Long>{

	public List<EhsRecord> findByIdCompany(Long idCompany);

}
