package com.example.Aktien_Rechner.converters;

import com.example.Aktien_Rechner.commands.UserCommand;
import com.example.Aktien_Rechner.domain.Order;
import com.example.Aktien_Rechner.domain.User;
import jakarta.annotation.Nullable;
import org.springframework.core.convert.converter.Converter;

public class UserToUserCommand implements Converter<User,UserCommand> {
    private final OrderToOrderCommand orderToOrderCommand;

    public UserToUserCommand(OrderToOrderCommand orderToOrderCommand) {
        this.orderToOrderCommand = orderToOrderCommand;
    }

    @Nullable
    @Override
    public UserCommand convert(User user) {
        if (user == null) {
            return null;
        }
        UserCommand userCommand = new UserCommand();
        userCommand.setId(user.getId());
        userCommand.setName(user.getName());

        if (user.getOrdersOpened() != null && user.getOrdersOpened().size()>0){
            user.getOrdersOpened().forEach((Order order) -> userCommand.getOrdersOpened().add(orderToOrderCommand.convert(order)));
        }

        if (user.getOrdersClosed() != null && user.getOrdersClosed().size()>0){
            user.getOrdersClosed().forEach((Order order) -> userCommand.getOrdersClosed().add(orderToOrderCommand.convert(order)));
        }

        return userCommand;
    }
}
