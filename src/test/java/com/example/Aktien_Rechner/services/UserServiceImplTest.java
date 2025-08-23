package com.example.Aktien_Rechner.services;

import com.example.Aktien_Rechner.domain.Transaction;
import com.example.Aktien_Rechner.domain.User;
import com.example.Aktien_Rechner.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

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

    @Test
    void getTransactionsPage_SuccessfulCase_ReturnsPagedTransactions() {
        // Arrange
        Long userId = 1L;
        int page = 0;
        int size = 2;
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("timestamp").descending());
        // Ожидаем, что транзакции отсортированы по timestamp в порядке убывания (позже -> раньше)
        List<Transaction> sortedTransactions = testTransactions.stream()
                .sorted((t1, t2) -> t2.getTimestamp().compareTo(t1.getTimestamp()))
                .toList();
        List<Transaction> pageContent = sortedTransactions.subList(0, 2); // Первые 2 транзакции
        Page<Transaction> expectedPage = new PageImpl<>(pageContent, pageRequest, testTransactions.size());

        when(userRepository.findById(userId)).thenReturn(Optional.of(testUser));

        // Act
        Page<Transaction> result = userService.getTransactionsPage(userId, page, size);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.getContent().size());
        assertEquals(5, result.getTotalElements());
        assertEquals(3, result.getTotalPages()); // 5 транзакций, size=2 -> 3 страницы (2+2+1)
        assertEquals(5L, result.getContent().get(0).getId()); // Последняя по времени (id=5)
        assertEquals(4L, result.getContent().get(1).getId()); // Предпоследняя (id=4)
        verify(userRepository).findById(userId);
    }

    @Test
    void getTransactionsPage_NoTransactions_ReturnsEmptyPage() {
        // Arrange
        Long userId = 1L;
        int page = 0;
        int size = 100;
        testUser.setTransactions(null); // Нет транзакций
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Transaction> expectedPage = new PageImpl<>(List.of(), pageRequest, 0);

        when(userRepository.findById(userId)).thenReturn(Optional.of(testUser));

        // Act
        Page<Transaction> result = userService.getTransactionsPage(userId, page, size);

        // Assert
        assertNotNull(result);
        assertTrue(result.getContent().isEmpty());
        assertEquals(0, result.getTotalElements());
        assertEquals(0, result.getTotalPages());
        verify(userRepository).findById(userId);
    }

    @Test
    void getTransactionsPage_UserNotFound_ThrowsRuntimeException() {
        // Arrange
        Long userId = 1L;
        int page = 0;
        int size = 100;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userService.getTransactionsPage(userId, page, size);
        });
        assertEquals("User not found.", exception.getMessage());
        verify(userRepository).findById(userId);
    }

    @Test
    void getTransactionsPage_NullUserId_ThrowsIllegalArgumentException() {
        // Arrange
        Long userId = null;
        int page = 0;
        int size = 100;

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.getTransactionsPage(userId, page, size);
        });
        assertEquals("User ID cannot be null", exception.getMessage());
        verify(userRepository, never()).findById(anyLong());
    }

    @Test
    void getTransactionsPage_OutOfBoundsPage_ReturnsEmptyPageContent() {
        // Arrange
        Long userId = 1L;
        int page = 100; // Запрашиваем страницу за пределами
        int size = 2;
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("timestamp").descending());
        Page<Transaction> expectedPage = new PageImpl<>(List.of(), pageRequest, testTransactions.size());

        when(userRepository.findById(userId)).thenReturn(Optional.of(testUser));

        // Act
        Page<Transaction> result = userService.getTransactionsPage(userId, page, size);

        // Assert
        assertNotNull(result);
        assertTrue(result.getContent().isEmpty()); // Пустая страница, так как page=100
        assertEquals(5, result.getTotalElements());
        assertEquals(3, result.getTotalPages()); // 5 транзакций, size=2 -> 3 страницы
        verify(userRepository).findById(userId);
    }
}