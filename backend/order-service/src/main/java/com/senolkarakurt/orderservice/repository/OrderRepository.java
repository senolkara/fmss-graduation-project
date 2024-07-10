package com.senolkarakurt.orderservice.repository;

import com.senolkarakurt.orderservice.model.Order;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {
    List<Order> findAllByCustomerId(Long customerId);
    Page<Order> findAllByCustomerId(Long customerId, Specification<Order> spec, Pageable pageable);
}
