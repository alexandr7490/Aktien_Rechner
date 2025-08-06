package com.example.Aktien_Rechner.converters;


import com.example.Aktien_Rechner.commands.OrderCommand;
import com.example.Aktien_Rechner.domain.Order;
import jakarta.annotation.Nullable;
import org.springframework.core.convert.converter.Converter;

public class OrderCommandToOrder  implements Converter<OrderCommand, Order> {

    private final UserCommandToUser userCommandToUser;
    private final ShareCommandToShare shareCommandToShare;

    public OrderCommandToOrder(UserCommandToUser userCommandToUser, ShareCommandToShare shareCommandToShare) {
        this.userCommandToUser = userCommandToUser;
        this.shareCommandToShare = shareCommandToShare;
    }

    @Nullable
    @Override
    public Order convert(OrderCommand orderCommand) {
        if (orderCommand == null) {
            return null;
        }

        Order order = new Order();
        order.setId(orderCommand.getId());
        order.setAmount(orderCommand.getAmount());
        order.setBuyPrice(orderCommand.getBuyPrice());
        order.setBuyTime(orderCommand.getBuyTime());
        order.setSellTime(orderCommand.getSellTime());
        order.setUser(userCommandToUser.convert(orderCommand.getUserCommand()));
        order.setShare(shareCommandToShare.convert(orderCommand.getShareCommand()));

        return order;
    }
}
