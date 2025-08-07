package com.example.Aktien_Rechner.commands;

import com.example.Aktien_Rechner.domain.Holding;
import com.example.Aktien_Rechner.domain.User;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PortfolioCommand {
    private Long id;
    private UserCommand user;
    private List<HoldingCommand> holdings = new ArrayList<>();
}
