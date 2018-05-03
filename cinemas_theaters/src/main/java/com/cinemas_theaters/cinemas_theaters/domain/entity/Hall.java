package com.cinemas_theaters.cinemas_theaters.domain.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;

public class Hall  implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "structures", fetch = FetchType.LAZY)
    ArrayList<Seat> seats;
}
