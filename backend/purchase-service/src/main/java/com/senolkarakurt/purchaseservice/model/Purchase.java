package com.senolkarakurt.purchaseservice.model;

import com.senolkarakurt.enums.RecordStatus;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "purchases")
public class Purchase implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "record_status", nullable = false)
    private RecordStatus recordStatus;

    @Column(name = "create_date_time", nullable = false)
    private LocalDateTime createDateTime;

    @Column(name = "total_price", nullable = false)
    private BigDecimal totalPrice;

    @Column(name = "order_id", nullable = false, unique = true)
    private Long orderId;

}
