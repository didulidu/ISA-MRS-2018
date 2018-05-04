package com.cinemas_theaters.cinemas_theaters.domain.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Entity
public class Projection  implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotNull
    private Date date;

    @Column(nullable = false)
    @NotNull
    private Show show;

    @ManyToOne
    private Hall hall;

    @Column(nullable = false)
    @NotNull
    private Integer price;
}
