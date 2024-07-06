package com.senolkarakurt.advertisementservice.model;

import com.senolkarakurt.enums.AdvertisementStatus;
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
@Table(name = "advertisements")
public class Advertisement implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "advertisement_status", nullable = false)
    private AdvertisementStatus advertisementStatus;

    @Column(name = "advertisement_no", nullable = false)
    private String advertisementNo;

    @Column(name = "start_date_time", nullable = false)
    private LocalDateTime startDateTime;

    @Column(name = "finish_date_time", nullable = false)
    private LocalDateTime finishDateTime;

    @Column(name = "building_id")
    private Long buildingId;

    @Column(name = "customer_package_id")
    private Long customerPackageId;

}
