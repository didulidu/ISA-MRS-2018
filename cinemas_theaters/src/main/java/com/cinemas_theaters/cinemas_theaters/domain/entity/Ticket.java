package com.cinemas_theaters.cinemas_theaters.domain.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Ticket  implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ticket_id")
    private Long id;


}
