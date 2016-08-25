package com.jms.repositories.q;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jms.domain.db.QNoProcess;
import com.jms.domain.db.QTester;



@Repository
public interface QNoProcessRepository extends JpaRepository<QNoProcess, Long>{

	public List<QNoProcess> getByIdCompany(Long idCompany);

}
