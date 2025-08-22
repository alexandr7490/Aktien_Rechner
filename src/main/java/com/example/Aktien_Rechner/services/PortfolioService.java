package com.example.Aktien_Rechner.services;

import java.math.BigDecimal;

public interface PortfolioService {
    public BigDecimal calculatePortfolioValue(Long userId);
}
