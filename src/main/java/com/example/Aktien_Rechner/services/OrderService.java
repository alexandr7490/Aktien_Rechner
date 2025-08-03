package com.example.Aktien_Rechner.services;

import com.example.Aktien_Rechner.domain.Order;

import java.util.Set;

public interface OrderService {
    Order findById(long id);

    Set<Order> getOpenedOrders();

    Set<Order> getClosedOrders();

    void deleteById(long id);

    void closeOrder(long id);
}
