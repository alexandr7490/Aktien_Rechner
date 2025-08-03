package com.example.Aktien_Rechner.services;

import com.example.Aktien_Rechner.domain.Order;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class OrderServiceImpl implements OrderService{
    @Override
    public Order findById(long id) {
        return null;
    }

    @Override
    public Set<Order> getOpenedOrders() {
        return Set.of();
    }

    @Override
    public Set<Order> getClosedOrders() {
        return Set.of();
    }

    @Override
    public void deleteById(long id) {

    }

    @Override
    public void closeOrder(long id) {

    }
}
