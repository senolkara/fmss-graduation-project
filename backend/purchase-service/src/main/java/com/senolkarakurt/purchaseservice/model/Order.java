package com.senolkarakurt.purchaseservice.model;

import com.senolkarakurt.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "orders")
public class Order implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "create_date_time", nullable = false)
    private LocalDateTime createDateTime;

    @Column(name = "order_code", nullable = false)
    private String orderCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status", nullable = false)
    private OrderStatus orderStatus;

    @Column(name = "package_id", nullable = false)
    private Long packageId;

    @Column(name = "customer_id", nullable = false)
    private Long customerId;

}
