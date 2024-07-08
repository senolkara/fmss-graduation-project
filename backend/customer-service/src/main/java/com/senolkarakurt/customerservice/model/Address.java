package com.senolkarakurt.customerservice.model;

import com.senolkarakurt.enums.RecordStatus;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "user_addresses")
public class Address implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "record_status", nullable = false)
    private RecordStatus recordStatus;

    @Column(name = "title")
    private String title;

    @Column(name = "province")
    private String province;

    @Column(name = "district")
    private String district;

    @Column(name = "neighbourhood")
    private String neighbourhood;

    @Column(name = "street")
    private String street;

    @Column(name = "description")
    private String description;

    @Column(name = "user_id")
    private Long userId;

}
