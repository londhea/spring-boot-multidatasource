package com.example.demo.alien;

import javax.persistence.*;

@Entity
@Table(name = "alien")
public class Alien {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long alienId;
    @Column
    private String alienName;
    @Column
    private String alienLocation;
    @Column
    private String alienContact;
}
