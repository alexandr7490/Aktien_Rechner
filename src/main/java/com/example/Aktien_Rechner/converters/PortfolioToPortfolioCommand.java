package com.example.Aktien_Rechner.converters;

import com.example.Aktien_Rechner.commands.PortfolioCommand;
import com.example.Aktien_Rechner.domain.Portfolio;
import jakarta.annotation.Nullable;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class PortfolioToPortfolioCommand implements Converter<Portfolio, PortfolioCommand> {

    private final HoldingToHoldingCommand holdingConverter;
    private final UserToUserCommand userConverter;

    public PortfolioToPortfolioCommand(HoldingToHoldingCommand holdingConverter, UserToUserCommand userConverter) {
        this.holdingConverter = holdingConverter;
        this.userConverter = userConverter;
    }

    @Nullable
    @Override
    synchronized public PortfolioCommand convert(Portfolio portfolio) {
        if (portfolio == null) {
            return null;
        }

        final PortfolioCommand portfolioCommand = new PortfolioCommand();
        portfolioCommand.setId(portfolio.getId());
        portfolioCommand.setUser(userConverter.convert(portfolio.getUser()));

        if (portfolio.getHoldings() != null && portfolio.getHoldings().size() > 0){
            portfolio.getHoldings()
                    .forEach( holding -> portfolioCommand.getHoldings().add(holdingConverter.convert(holding)));
        }
        return portfolioCommand;
    }
}
