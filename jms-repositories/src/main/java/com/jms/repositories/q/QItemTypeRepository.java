package com.jms.repositories.q;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.jms.domain.db.QItemType;


@Repository
public interface QItemTypeRepository extends JpaRepository<QItemType, Long>{


}
