package com.example.Aktien_Rechner.controllers;

import com.example.Aktien_Rechner.repository.UserRepository;
import com.example.Aktien_Rechner.services.PortfolioService;
import com.example.Aktien_Rechner.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserController {

    private final UserService userService;
    private final PortfolioService portfolioService;

    public UserController(UserService userService, PortfolioService portfolioService) {
        this.userService = userService;
        this.portfolioService = portfolioService;
    }


    @GetMapping
    @RequestMapping("user/{userId}/show")
    public String getUsers(@PathVariable String userId, Model model) {
        model.addAttribute("user", userService.findById(Long.valueOf(userId)));
        model.addAttribute("portfolioValue", portfolioService.calculatePortfolioValue(Long.valueOf(userId)));
        model.addAttribute("lastTransactions", userService.getLastNTransactions(Long.valueOf(userId),5));
        return "user";
    }
}
