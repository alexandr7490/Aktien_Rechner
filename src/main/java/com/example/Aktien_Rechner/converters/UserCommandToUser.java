package com.example.Aktien_Rechner.converters;

import com.example.Aktien_Rechner.commands.OrderCommand;
import com.example.Aktien_Rechner.commands.UserCommand;
import com.example.Aktien_Rechner.domain.Order;
import com.example.Aktien_Rechner.domain.User;
import jakarta.annotation.Nullable;
import org.springframework.core.convert.converter.Converter;

public class UserCommandToUser implements Converter<UserCommand, User> {

    private final OrderCommandToOrder orderCommandToOrder;

    public UserCommandToUser(OrderCommandToOrder orderCommandToOrder) {
        this.orderCommandToOrder = orderCommandToOrder;
    }

    @Nullable
    @Override
    public User convert(UserCommand userCommand) {
        if (userCommand == null) {
            return null;
        }
        User user = new User();
        user.setId(userCommand.getId());
        user.setName(userCommand.getName());

        if (userCommand.getOrdersOpened() != null && userCommand.getOrdersOpened().size()>0){
            userCommand.getOrdersOpened().forEach((OrderCommand orderCommand) -> user.getOrdersOpened().add(orderCommandToOrder.convert(orderCommand)));
        }

        if (userCommand.getOrdersClosed() != null && userCommand.getOrdersClosed().size()>0){
            userCommand.getOrdersClosed().forEach((OrderCommand orderCommand) -> user.getOrdersClosed().add(orderCommandToOrder.convert(orderCommand)));
        }

        return user;
    }
}
