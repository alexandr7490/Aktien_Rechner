package com.example.Aktien_Rechner.controllers;

import com.example.Aktien_Rechner.domain.Transaction;
import com.example.Aktien_Rechner.services.UserService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@Controller
public class TransactionController {
    private final UserService userService;

    public TransactionController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @RequestMapping("user/{userId}/transactions/show")
    public String getAllTransactions(@PathVariable("userId") String userId,
                                     @RequestParam(value = "page", defaultValue = "0") int page,
                                     Model model) {
        int pageSize = 100;
        Page<Transaction> transactionPage = userService.getTransactionsPage(Long.valueOf(userId), page, pageSize);
        model.addAttribute("user", userService.findById(Long.valueOf(userId)));
        model.addAttribute("transactions", transactionPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", transactionPage.getTotalPages());

        return "transactions/allTransactions";
    }
}
