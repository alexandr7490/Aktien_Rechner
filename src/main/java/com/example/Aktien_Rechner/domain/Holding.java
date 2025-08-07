package com.example.Aktien_Rechner.domain;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Holding {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double quantity;
    @ManyToOne
    @JoinColumn(name = "portfolio_id")
    private Portfolio portfolio;
    @ManyToOne
    @JoinColumn(name = "share_id")
    private Share share;

}
