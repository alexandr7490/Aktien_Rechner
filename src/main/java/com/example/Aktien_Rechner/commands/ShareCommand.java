package com.example.Aktien_Rechner.commands;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ShareCommand {
    private Long id;
    private String name;
    private String shortName;
    private BigDecimal actualPrice;
}

