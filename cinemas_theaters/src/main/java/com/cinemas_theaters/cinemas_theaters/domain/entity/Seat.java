package com.cinemas_theaters.cinemas_theaters.domain.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Entity
public class Seat  implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seat_id")
    private Long id;

    @Column(nullable = false)
    private Integer chairRow;

    @Column(nullable = false)
    @NotNull
    private Integer chairNumber;

    @ManyToOne
    //@JoinColumn(name = "hall_id")
    private Hall hall;

}
