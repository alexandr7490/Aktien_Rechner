package com.example.Aktien_Rechner.services;

import com.example.Aktien_Rechner.domain.Holding;
import com.example.Aktien_Rechner.domain.Portfolio;
import com.example.Aktien_Rechner.domain.Share;
import com.example.Aktien_Rechner.domain.User;
import com.example.Aktien_Rechner.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class PortfolioServiceImplTest {
    private UserRepository userRepository;
    private PortfolioService portfolioService;

    private User testUser;
    private Portfolio testPortfolio;
    private List<Holding> testHoldings;
    private Share testShare1;
    private Share testShare2;

    @BeforeEach
    void setUp() {
        // Создаём мок для UserRepository
        userRepository = mock(UserRepository.class);

        // Создаём PortfolioServiceImpl, передавая мок
        portfolioService = new PortfolioServiceImpl(userRepository);

        // Инициализация тестовых данных
        testUser = new User();
        testUser.setId(1L);
        testUser.setName("TestUser");

        testPortfolio = new Portfolio();
        testPortfolio.setUser(testUser);

        testHoldings = new ArrayList<>();
        testShare1 = new Share();
        testShare1.setName("AAA");
        testShare1.setActualPrice(new BigDecimal("100.00"));

        testShare2 = new Share();
        testShare2.setName("BBB");
        testShare2.setActualPrice(new BigDecimal("200.00"));

        Holding holding1 = new Holding();
        holding1.setShare(testShare1);
        holding1.setQuantity(5.0);
        holding1.setPortfolio(testPortfolio);

        Holding holding2 = new Holding();
        holding2.setShare(testShare2);
        holding2.setQuantity(10.0);
        holding2.setPortfolio(testPortfolio);

        testHoldings.add(holding1);
        testHoldings.add(holding2);

        testPortfolio.setHoldings(testHoldings);
        testUser.setPortfolio(testPortfolio);
    }

    @Test
    void calculatePortfolioValue_SuccessfulCase_ReturnsCorrectSum() {

        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.of(testUser));

        BigDecimal result = portfolioService.calculatePortfolioValue(userId);

        assertEquals(new BigDecimal("2500.000"), result);
        verify(userRepository).findById(userId);
    }

    @Test
    void calculatePortfolioValue_UserNotFound_ThrowsRuntimeException() {

        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> portfolioService.calculatePortfolioValue(userId));
        assertEquals("User not found.", exception.getMessage());
        verify(userRepository).findById(userId);
    }

    @Test
    void calculatePortfolioValue_NullPortfolio_ReturnsZero() {

        Long userId = 1L;
        testUser.setPortfolio(null);
        when(userRepository.findById(userId)).thenReturn(Optional.of(testUser));

        BigDecimal result = portfolioService.calculatePortfolioValue(userId);

        assertEquals(BigDecimal.ZERO, result);
        verify(userRepository).findById(userId);
    }

    @Test
    void calculatePortfolioValue_EmptyHoldings_ReturnsZero() {

        Long userId = 1L;
        testPortfolio.setHoldings(new ArrayList<>());
        when(userRepository.findById(userId)).thenReturn(Optional.of(testUser));

        BigDecimal result = portfolioService.calculatePortfolioValue(userId);

        assertEquals(BigDecimal.ZERO, result);
        verify(userRepository).findById(userId);
    }

    @Test
    void calculatePortfolioValue_NullShareOrPrice_IgnoresInvalidHoldings() {
        // Arrange
        Long userId = 1L;
        Holding invalidHolding = new Holding();
        invalidHolding.setShare(null);
        invalidHolding.setQuantity(3.0);
        invalidHolding.setPortfolio(testPortfolio);

        Holding holdingWithNullPrice = new Holding();
        Share nullPriceShare = new Share();
        nullPriceShare.setName("Invalid");
        nullPriceShare.setActualPrice(null);
        holdingWithNullPrice.setShare(nullPriceShare);
        holdingWithNullPrice.setQuantity(4.0);
        holdingWithNullPrice.setPortfolio(testPortfolio);

        testHoldings.add(invalidHolding);
        testHoldings.add(holdingWithNullPrice);
        when(userRepository.findById(userId)).thenReturn(Optional.of(testUser));

        // Act
        BigDecimal result = portfolioService.calculatePortfolioValue(userId);


        assertEquals(new BigDecimal("2500.000"), result);
        verify(userRepository).findById(userId);
    }
}