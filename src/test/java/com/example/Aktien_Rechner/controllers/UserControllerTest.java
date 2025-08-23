package com.example.Aktien_Rechner.controllers;

import com.example.Aktien_Rechner.domain.Transaction;
import com.example.Aktien_Rechner.domain.User;
import com.example.Aktien_Rechner.services.PortfolioService;
import com.example.Aktien_Rechner.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {
    @Mock
    private UserService userService;

    @Mock
    private PortfolioService portfolioService;

    @Mock
    private Model model;

    @InjectMocks
    private UserController userController;

    private User testUser;
    private List<Transaction> testTransactions;
    private BigDecimal testPortfolioValue;

    @BeforeEach
    void setUp() {
        // Инициализация тестовых данных
        testUser = new User();
        testUser.setId(1L);
        testUser.setName("TestUser");

        testTransactions = new ArrayList<>();
        Transaction transaction = new Transaction();
        transaction.setId(1L);
        testTransactions.add(transaction);

        testPortfolioValue = new BigDecimal("1000.00");
    }
    @Test
    void getUsers_SuccessfulCase_ReturnsUserTemplate() {
        // Arrange
        String userId = "1";
        when(userService.findById(1L)).thenReturn(testUser);
        when(portfolioService.calculatePortfolioValue(1L)).thenReturn(testPortfolioValue);
        when(userService.getLastNTransactions(1L, 5)).thenReturn(testTransactions);

        // Act
        String result = userController.getUsers(userId, model);

        // Assert
        assertEquals("user", result);
        verify(model).addAttribute("user", testUser);
        verify(model).addAttribute("portfolioValue", testPortfolioValue);
        verify(model).addAttribute("lastTransactions", testTransactions);
        verify(userService).findById(1L);
        verify(portfolioService).calculatePortfolioValue(1L);
        verify(userService).getLastNTransactions(1L, 5);
    }
}