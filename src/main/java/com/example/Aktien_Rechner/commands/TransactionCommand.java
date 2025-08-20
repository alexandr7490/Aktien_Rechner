package com.example.Aktien_Rechner.commands;

import com.example.Aktien_Rechner.domain.Share;
import com.example.Aktien_Rechner.domain.TransactionType;
import com.example.Aktien_Rechner.domain.User;
import lombok.Data;

import java.sql.Date;
import java.time.LocalDateTime;

@Data
public class TransactionCommand {
    private Long id;
    private double quantity;
    private double price;
    private LocalDateTime timestamp;

    private ShareCommand share;
    private TransactionType type;
}
