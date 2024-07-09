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
@Table(name = "villas")
public class Villa extends Building implements Serializable {

    @Column(name = "is_there_a_pool")
    private boolean isThereAPool;

}
