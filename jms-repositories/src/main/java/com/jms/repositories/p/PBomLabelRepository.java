package com.jms.repositories.p;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.jms.domain.db.PBomLabel;


@Repository
public interface PBomLabelRepository extends JpaRepository<PBomLabel, Long>{

  @Query("select p from PBomLabel p where p.company.idCompany=?1")
  public List<PBomLabel> getByCompanyId(Long companyId);
}
