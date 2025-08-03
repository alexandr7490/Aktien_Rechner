package com.example.Aktien_Rechner.controllers;

import com.example.Aktien_Rechner.services.OrderService;
import com.example.Aktien_Rechner.services.ShareService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
    private final OrderService orderService;
    private final ShareService shareService;

    public IndexController(OrderService orderService, ShareService shareService) {
        this.orderService = orderService;
        this.shareService = shareService;
    }

    @RequestMapping({"","/shares"})
    public String getSharesPage(Model model){
        model.addAttribute("shares", shareService.getShares());
        return "shares";
    }
    @RequestMapping({"","/orders"})
    public String getOrdersPage(Model model){
        model.addAttribute("orders", orderService.getOpenedOrders());
        return "orders";
    }
}
