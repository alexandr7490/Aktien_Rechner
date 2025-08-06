package com.example.Aktien_Rechner.commands;

import com.example.Aktien_Rechner.domain.Share;
import com.example.Aktien_Rechner.domain.User;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;
@Data
public class OrderCommand {
    private long id;
    private double amount;
    private double buyPrice;
    private Date buyTime;
    private Date sellTime;
    private UserCommand userCommand;
    private ShareCommand shareCommand;
}
