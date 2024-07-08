package com.senolkarakurt.advertisementservice.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.senolkarakurt.enums.RecordStatus;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = House.class, name = "house"),
        @JsonSubTypes.Type(value = SummerHouse.class, name = "summerhouse"),
        @JsonSubTypes.Type(value = Villa.class, name = "villa")
})
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Building implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "record_status", nullable = false)
    private RecordStatus recordStatus;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "square_meters", nullable = false)
    private Integer squareMeters;

    @Column(name = "room_count", nullable = false)
    private Integer roomCount;

    @Column(name = "saloon_count", nullable = false)
    private Integer saloonCount;

    @Column(name = "floor_count", nullable = false)
    private Integer floorCount;

    @Column(name = "how_old_is_it", nullable = false)
    private Integer howOldIsIt;

}
