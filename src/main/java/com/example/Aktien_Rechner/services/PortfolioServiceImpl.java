package com.example.Aktien_Rechner.services;

import com.example.Aktien_Rechner.domain.User;
import com.example.Aktien_Rechner.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class PortfolioServiceImpl implements PortfolioService{

    private final UserRepository userRepository;

    public PortfolioServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public BigDecimal calculatePortfolioValue(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new RuntimeException("User not found.");
        }
        User user = userOptional.get();

        if (user.getPortfolio() == null || user.getPortfolio().getHoldings() == null) {
            return BigDecimal.ZERO;
        }
        return user.getPortfolio().getHoldings().stream()
                .filter(holding -> holding.getShare() != null && holding.getShare().getActualPrice() != null)
                .map(holding -> holding.getShare().getActualPrice()
                        .multiply(BigDecimal.valueOf(holding.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
