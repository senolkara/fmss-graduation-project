package com.senolkarakurt.cpackageservice.repository;

import com.senolkarakurt.cpackageservice.model.SystemLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SystemLogRepository extends JpaRepository<SystemLog, Long> {
}
