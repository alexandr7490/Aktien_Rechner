package com.example.Aktien_Rechner.commands;

import com.example.Aktien_Rechner.domain.Portfolio;
import com.example.Aktien_Rechner.domain.Share;
import lombok.Data;

@Data
public class HoldingCommand {
    private Long id;
    private double quantity;

    private ShareCommand share;
}
