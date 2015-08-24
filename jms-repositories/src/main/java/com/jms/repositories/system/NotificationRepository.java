package com.jms.repositories.system;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.jms.domain.db.Notification;


@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long>{


}
