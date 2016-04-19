package com.jms.repositories.p;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.jms.domain.db.PAttDraw;
import com.jms.domain.db.PBomLabel;
import com.jms.domain.db.PDraw;


@Repository
public interface PDrawRepository extends JpaRepository<PDraw, Long>{


}
