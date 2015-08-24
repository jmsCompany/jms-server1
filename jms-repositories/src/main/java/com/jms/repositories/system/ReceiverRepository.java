package com.jms.repositories.system;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.jms.domain.db.Receiver;


@Repository
public interface ReceiverRepository extends JpaRepository<Receiver, Long>{


}
