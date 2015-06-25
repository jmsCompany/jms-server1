package com.jms.repositories.user;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jms.domain.db.Groups;

@Repository
public interface GroupRepository extends CrudRepository<Groups, Long>{

}
