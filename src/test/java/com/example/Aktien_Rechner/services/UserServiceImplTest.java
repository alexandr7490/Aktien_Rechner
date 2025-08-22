package com.example.Aktien_Rechner.services;

import com.example.Aktien_Rechner.domain.Transaction;
import com.example.Aktien_Rechner.domain.User;
import com.example.Aktien_Rechner.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    private UserRepository userRepository;
    private UserService userService;

    private User testUser;
    private List<Transaction> testTransactions;

    @BeforeEach
    void setUp() {

        userRepository = mock(UserRepository.class);
        userService = new UserServiceImpl(userRepository);

        testUser = new User();
        testUser.setId(1L);
        testUser.setName("TestUser");

        testTransactions = new ArrayList<>();

        for (int i = 1; i <= 5; i++) {
            Transaction transaction = new Transaction();
            transaction.setId((long) i);
            transaction.setTimestamp(LocalDateTime.of(2025, 8, i, 12, 0));
            transaction.setQuantity(i * 10);
            transaction.setUser(testUser);
            testTransactions.add(transaction);
        }
        testUser.setTransactions(testTransactions);
    }

    @Test
    void getLastNTransactions_SuccessfulCase_ReturnsLastNTransactions() {

        Long userId = 1L;
        int amount = 3;
        when(userRepository.findById(userId)).thenReturn(Optional.of(testUser));

        List<Transaction> result = userService.getLastNTransactions(userId, amount);

        assertEquals(amount, result.size(), "Should return exactly 3 transactions");
        // Проверяем, что транзакции отсортированы по timestamp в порядке убывания
        assertEquals(5L, result.get(0).getId(), "First transaction should be the latest (ID=5)");
        assertEquals(4L, result.get(1).getId(), "Second transaction should be ID=4");
        assertEquals(3L, result.get(2).getId(), "Third transaction should be ID=3");
        // Проверяем timestamp
        assertEquals(LocalDateTime.of(2025, 8, 5, 12, 0), result.get(0).getTimestamp());
        assertEquals(LocalDateTime.of(2025, 8, 4, 12, 0), result.get(1).getTimestamp());
        assertEquals(LocalDateTime.of(2025, 8, 3, 12, 0), result.get(2).getTimestamp());
        verify(userRepository).findById(userId);
    }

    @Test
    void getLastNTransactions_NullTransactions_ReturnsEmptyList() {

        Long userId = 1L;
        int amount = 3;
        testUser.setTransactions(null);
        when(userRepository.findById(userId)).thenReturn(Optional.of(testUser));

        List<Transaction> result = userService.getLastNTransactions(userId, amount);

        assertTrue(result.isEmpty(), "Should return empty list when transactions are null");
        verify(userRepository).findById(userId);
    }

    @Test
    void getLastNTransactions_AllTimestampsNull_ReturnsEmptyList() {

        Long userId = 1L;
        int amount = 3;
        testTransactions.forEach(transaction -> transaction.setTimestamp(null));
        when(userRepository.findById(userId)).thenReturn(Optional.of(testUser));

        List<Transaction> result = userService.getLastNTransactions(userId, amount);

        assertTrue(result.isEmpty(), "Should return empty list when all timestamps are null");
        verify(userRepository).findById(userId);
    }

    @Test
    void getLastNTransactions_AmountGreaterThanSize_ReturnsAllTransactions() {

        Long userId = 1L;
        int amount = 10; // Больше, чем 5 транзакций
        when(userRepository.findById(userId)).thenReturn(Optional.of(testUser));

        List<Transaction> result = userService.getLastNTransactions(userId, amount);

        assertEquals(5, result.size(), "Should return all 5 transactions");
        assertEquals(5L, result.get(0).getId(), "First transaction should be the latest (ID=5)");
        assertEquals(1L, result.get(4).getId(), "Last transaction should be the earliest (ID=1)");
        verify(userRepository).findById(userId);
    }

    @Test
    void getLastNTransactions_ZeroOrNegativeAmount_ReturnsEmptyList() {

        Long userId = 1L;
        int amount = 0; // Тестируем amount = 0
        when(userRepository.findById(userId)).thenReturn(Optional.of(testUser));

        List<Transaction> result = userService.getLastNTransactions(userId, amount);

        assertTrue(result.isEmpty(), "Should return empty list when amount is 0");
        verify(userRepository).findById(userId);
    }

    @Test
    void findById_SuccessfulCase_ReturnsUser() {
        // Arrange
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.of(testUser));

        // Act
        User result = userService.findById(userId);

        // Assert
        assertNotNull(result, "User should not be null");
        assertEquals(testUser.getId(), result.getId(), "User ID should match");
        assertEquals(testUser.getName(), result.getName(), "User name should match");
        verify(userRepository).findById(userId);
    }

    @Test
    void findById_UserNotFound_ThrowsUserNotFoundException() {
        // Arrange
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> userService.findById(userId));
        assertEquals("User not found.", exception.getMessage());
        verify(userRepository).findById(userId);
    }
}