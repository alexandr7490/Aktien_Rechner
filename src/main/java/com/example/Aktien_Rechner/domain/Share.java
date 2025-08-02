package com.example.Aktien_Rechner.domain;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.boot.autoconfigure.web.WebProperties;

@Data
@Entity
public class Share {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String shortName;
    private Integer actualPrice;
    @OneToMany
    @JoinColumn(name="order")
    private Order order;
}
