package com.senolkarakurt.advertisementservice.repository;

import com.senolkarakurt.advertisementservice.model.Building;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface BuildingRepository extends JpaRepository<Building, Long>, JpaSpecificationExecutor<Building> {
}
