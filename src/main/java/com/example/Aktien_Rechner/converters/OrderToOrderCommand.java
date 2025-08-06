package com.example.Aktien_Rechner.converters;


import com.example.Aktien_Rechner.commands.OrderCommand;
import com.example.Aktien_Rechner.domain.Order;
import jakarta.annotation.Nullable;
import org.springframework.core.convert.converter.Converter;

import java.sql.Date;

public class OrderToOrderCommand  implements Converter<Order, OrderCommand> {

    private final UserToUserCommand userToUserCommand;
    private final ShareToShareCommand shareToShareCommand;

    public OrderToOrderCommand(UserToUserCommand userToUserCommand, ShareToShareCommand shareToShareCommand) {
        this.userToUserCommand = userToUserCommand;
        this.shareToShareCommand = shareToShareCommand;
    }

    @Nullable
    @Override
    public OrderCommand convert(Order order) {
        if (order == null) {
            return null;
        }

        OrderCommand orderCommand = new OrderCommand();
        orderCommand.setId(order.getId());
        orderCommand.setAmount(order.getAmount());
        orderCommand.setBuyPrice(order.getBuyPrice());
        orderCommand.setBuyTime(order.getBuyTime());
        orderCommand.setSellTime(order.getSellTime());
        orderCommand.setUserCommand(userToUserCommand.convert(order.getUser()));
        orderCommand.setShareCommand(shareToShareCommand.convert(order.getShare()));

        return orderCommand;
    }
}