package com.example.Aktien_Rechner.converters;

import com.example.Aktien_Rechner.commands.PortfolioCommand;
import com.example.Aktien_Rechner.domain.Portfolio;
import jakarta.annotation.Nullable;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class PortfolioCommandToPortfolio implements Converter<PortfolioCommand, Portfolio> {

    private final HoldingCommandToHolding holdingConverter;
    private final UserCommandToUser userConverter;

    public PortfolioCommandToPortfolio(HoldingCommandToHolding holdingConverter, UserCommandToUser userConverter) {
        this.holdingConverter = holdingConverter;
        this.userConverter = userConverter;
    }

    @Nullable
    @Override
    synchronized public Portfolio convert(PortfolioCommand portfolioCommand) {
        if (portfolioCommand == null) {
            return null;
        }

        final Portfolio portfolio = new Portfolio();
        portfolio.setId(portfolioCommand.getId());
        portfolio.setUser(userConverter.convert(portfolioCommand.getUser()));

        if (portfolioCommand.getHoldings() != null && portfolioCommand.getHoldings().size() > 0){
            portfolioCommand.getHoldings()
                    .forEach( holdingCommand -> portfolio.getHoldings().add(holdingConverter.convert(holdingCommand)));
        }
        return portfolio;
    }
}
