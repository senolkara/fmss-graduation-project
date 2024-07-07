package com.senolkarakurt.cpackageservice.repository;

import com.senolkarakurt.cpackageservice.model.CPackage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PackageRepository extends JpaRepository<CPackage, Long> {
}
