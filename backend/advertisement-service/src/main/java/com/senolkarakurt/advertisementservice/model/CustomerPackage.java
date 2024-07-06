package com.senolkarakurt.advertisementservice.model;

import com.senolkarakurt.enums.RecordStatus;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "customer_packages")
public class CustomerPackage implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "record_status", nullable = false)
    private RecordStatus recordStatus;

    @Column(name = "start_date_time", nullable = false)
    private LocalDateTime startDateTime;

    @Column(name = "finish_date_time", nullable = false)
    private LocalDateTime finishDateTime;

    @Column(name = "advertisement_count", nullable = false)
    private Integer advertisementCount;

    @Column(name = "package_id", nullable = false)
    private Long packageId;

    @Column(name = "customer_id", nullable = false)
    private Long customerId;
}
