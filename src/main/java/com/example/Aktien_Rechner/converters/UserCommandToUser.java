package com.example.Aktien_Rechner.converters;

import com.example.Aktien_Rechner.commands.ShareCommand;
import com.example.Aktien_Rechner.commands.UserCommand;
import com.example.Aktien_Rechner.domain.Share;
import com.example.Aktien_Rechner.domain.User;
import jakarta.annotation.Nullable;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserCommandToUser implements Converter<UserCommand, User> {

    private final PortfolioCommandToPortfolio portfolioConverter;
    private final TransactionCommandToTransaction transactionConverter;

    public UserCommandToUser(PortfolioCommandToPortfolio portfolioConverter, TransactionCommandToTransaction transactionConverter) {
        this.portfolioConverter = portfolioConverter;
        this.transactionConverter = transactionConverter;
    }


    @Nullable
    @Override
    synchronized public User convert(UserCommand userCommand) {
        if (userCommand == null) {
            return null;
        }

        final User user = new User();
        user.setId(userCommand.getId());
        user.setName(userCommand.getName());
        user.setPortfolio(portfolioConverter.convert(userCommand.getPortfolio()));
        if (userCommand.getTransactions() != null && userCommand.getTransactions().size() > 0){
            userCommand.getTransactions()
                    .forEach( transaction -> user.getTransactions().add(transactionConverter.convert(transaction)));
        }
        return user;
    }
}
