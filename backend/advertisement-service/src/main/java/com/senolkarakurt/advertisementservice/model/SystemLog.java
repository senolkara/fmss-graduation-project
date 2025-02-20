package com.senolkarakurt.advertisementservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "system_logs")
public class SystemLog implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "start_date_time", nullable = false)
    private LocalDateTime recordDateTime;

    @Column(name = "content", nullable = false)
    private String content;
}
