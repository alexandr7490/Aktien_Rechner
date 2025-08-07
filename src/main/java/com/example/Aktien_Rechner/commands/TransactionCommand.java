package com.example.Aktien_Rechner.commands;

import com.example.Aktien_Rechner.domain.Share;
import com.example.Aktien_Rechner.domain.TransactionType;
import com.example.Aktien_Rechner.domain.User;
import lombok.Data;

import java.sql.Date;

@Data
public class TransactionCommand {
    private Long id;
    private double quantity;
    private double price;
    private Date timestamp;
    private UserCommand user;
    private ShareCommand share;
    private TransactionType type;
}
