package com.example.Aktien_Rechner.services;

import com.example.Aktien_Rechner.commands.UserCommand;
import com.example.Aktien_Rechner.domain.Transaction;
import com.example.Aktien_Rechner.domain.User;
import com.example.Aktien_Rechner.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserCommand saveUserCommand(UserCommand userCommand) {
        return null;
    }

    @Override
    public User findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()){
            throw new RuntimeException("User not found.");//Можно добавить своё иключение!
        }
        return userOptional.get();
    }

    @Override
    public List<Transaction> getLastNTransactions(Long userId, Integer amount) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new RuntimeException("User not found.");
        }
        User user = userOptional.get();
        if (user.getTransactions() == null) {
            return List.of();
        }
        return user.getTransactions().stream()
                .filter(transaction -> transaction.getTimestamp() != null)
                .sorted(Comparator.comparing(Transaction::getTimestamp).reversed())
                .limit(amount)
                .collect(Collectors.toList());
    }

    @Override
    public Page<Transaction> getTransactionsPage(Long userId, int page, int size) {
        if (userId == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new RuntimeException("User not found.");
        }
        User user = userOptional.get();

        if (user.getTransactions() == null) {
            return new PageImpl<>(List.of(), PageRequest.of(page, size), 0);
        }

        List<Transaction> sortedTransactions = user.getTransactions().stream()
                .filter(transaction -> transaction.getTimestamp() != null)
                .sorted(Comparator.comparing(Transaction::getTimestamp).reversed())
                .collect(Collectors.toList());

        System.out.println("Total transactions: " + sortedTransactions.size());
        System.out.println("Total pages: " + (int) Math.ceil((double) sortedTransactions.size() / size));
        int start = Math.min(page * size, sortedTransactions.size());
        int end = Math.min((page + 1) * size, sortedTransactions.size());
        List<Transaction> pageContent = sortedTransactions.subList(start, end);

        return new PageImpl<>(pageContent, PageRequest.of(page, size), sortedTransactions.size());
    }


}
