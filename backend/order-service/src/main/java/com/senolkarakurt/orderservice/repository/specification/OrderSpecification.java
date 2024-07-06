package com.senolkarakurt.orderservice.repository.specification;

import com.senolkarakurt.enums.OrderStatus;
import com.senolkarakurt.orderservice.model.Order;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderSpecification {
    public static Specification<Order> hasOrderCode(String orderCode) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("orderCode"), orderCode);
    }

    public static Specification<Order> hasOrderStatus(OrderStatus orderStatus) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("orderStatus"), orderStatus);
    }

    public static Specification<Order> hasCreateDateTime(LocalDate createDate) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("createDateTime"), createDate.atStartOfDay());
    }
}
