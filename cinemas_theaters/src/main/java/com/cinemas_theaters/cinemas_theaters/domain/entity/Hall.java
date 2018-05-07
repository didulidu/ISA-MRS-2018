package com.cinemas_theaters.cinemas_theaters.domain.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "`hall`")
public class Hall  implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hall_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "hall",fetch = FetchType.LAZY)
    private List<Seat> seats;

    /*@ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "shows_halls",
            joinColumns = @JoinColumn(name = "show_id"),
            inverseJoinColumns = @JoinColumn(name = "hall_id")
    )
    //tabela sa filmovima/predstavama i njihovim salama (salama i njihovim filmovima/predstavama)
    private List<Show> shows;*/

    @ManyToOne
    //@JoinColumn(name = "theatre_id")
    private Theatre theatre;
}
