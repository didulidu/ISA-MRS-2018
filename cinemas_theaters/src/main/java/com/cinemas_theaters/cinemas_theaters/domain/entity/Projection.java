package com.cinemas_theaters.cinemas_theaters.domain.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "`projection`")
public class Projection  implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "projection_id")
    private Long id;

    @Column(nullable = false)
    @NotNull
    private Date date;

    @Column(nullable = false)
    //@JoinColumn(name = "show_id")
    @NotNull
    private Show show;

    @ManyToOne
    //@JoinColumn(name = "hall_id")
    private Hall hall;

    @Column(nullable = false)
    @NotNull
    private Integer price;
}
