package com.example.Aktien_Rechner.commands;

import com.example.Aktien_Rechner.domain.Order;
import lombok.Data;

@Data
public class ShareCommand {
    private Long id;
    private String name;
    private String shortName;
    private Integer actualPrice;
    private OrderCommand orderCommand;
}
