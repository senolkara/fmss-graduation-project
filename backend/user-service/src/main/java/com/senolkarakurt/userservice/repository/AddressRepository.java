package com.senolkarakurt.userservice.repository;

import com.senolkarakurt.enums.RecordStatus;
import com.senolkarakurt.userservice.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    List<Address> findAddressesByUserIdAndRecordStatus(Long userId, RecordStatus recordStatus);
}
