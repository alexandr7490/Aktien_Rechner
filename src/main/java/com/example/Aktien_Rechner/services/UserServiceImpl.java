package com.example.Aktien_Rechner.services;

import com.example.Aktien_Rechner.commands.UserCommand;
import com.example.Aktien_Rechner.domain.Transaction;
import com.example.Aktien_Rechner.domain.User;
import com.example.Aktien_Rechner.repository.UserRepository;
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
}
