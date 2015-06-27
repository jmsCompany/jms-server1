package com.jms.repositories.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jms.domain.db.Groups;

@Repository
public interface GroupRepository extends JpaRepository<Groups, Long>{

}
