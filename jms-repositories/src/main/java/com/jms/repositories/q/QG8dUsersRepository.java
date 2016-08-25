package com.jms.repositories.q;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.jms.domain.db.QG8dUsers;





@Repository
public interface QG8dUsersRepository extends JpaRepository<QG8dUsers, Long>{


}
