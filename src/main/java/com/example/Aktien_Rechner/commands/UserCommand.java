package com.example.Aktien_Rechner.commands;

import com.example.Aktien_Rechner.domain.Order;
import lombok.Data;

import java.util.HashSet;
@Data
public class UserCommand {

    private Long id;
    private String name;
    private HashSet<OrderCommand> ordersOpened = new HashSet<>();
    private HashSet<OrderCommand> ordersClosed = new HashSet<>();


}
