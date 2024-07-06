package com.senolkarakurt.advertisementservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "houses")
public class House extends Building implements Serializable {

    @Column(name = "which_floor")
    private Integer whichFloor;

    @Column(name = "is_detached")
    private boolean isDetached;

}
