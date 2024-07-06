package com.senolkarakurt.advertisementservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "building_addresses")
public class BuildingAddress implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

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

    @Column(name = "building_id")
    private Long buildingId;

}
