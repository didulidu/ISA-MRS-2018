package com.cinemas_theaters.cinemas_theaters.domain.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;

public class Cinema {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    @NotNull
    private ArrayList<Hall> halls;

    @Column(nullable = false)
    @NotNull
    private ArrayList<Show> shows;



}
