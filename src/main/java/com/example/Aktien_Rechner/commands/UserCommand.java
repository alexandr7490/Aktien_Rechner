package com.example.Aktien_Rechner.commands;

import com.example.Aktien_Rechner.domain.Portfolio;
import com.example.Aktien_Rechner.domain.Transaction;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserCommand {
    private Long id;
    private String name;
    private PortfolioCommand portfolio;
    private List<TransactionCommand> transactions = new ArrayList<>();
}
