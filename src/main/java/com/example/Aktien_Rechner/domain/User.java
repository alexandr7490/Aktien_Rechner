package com.example.Aktien_Rechner.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;

@Data
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private HashSet<Order> ordersOpened = new HashSet<>();
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private HashSet<Order> ordersClosed = new HashSet<>();
}
