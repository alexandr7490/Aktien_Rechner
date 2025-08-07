package com.example.Aktien_Rechner.converters;

import com.example.Aktien_Rechner.commands.ShareCommand;
import com.example.Aktien_Rechner.commands.UserCommand;
import com.example.Aktien_Rechner.domain.Portfolio;
import com.example.Aktien_Rechner.domain.Share;
import com.example.Aktien_Rechner.domain.Transaction;
import com.example.Aktien_Rechner.domain.User;
import jakarta.annotation.Nullable;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserToUserCommand implements Converter<User, UserCommand> {

    private final PortfolioToPortfolioCommand portfolioConverter;
    private final TransactionToTransactionCommand transactionConverter;

    public UserToUserCommand(PortfolioToPortfolioCommand portfolioConverter, TransactionToTransactionCommand transactionConverter) {
        this.portfolioConverter = portfolioConverter;
        this.transactionConverter = transactionConverter;
    }


    @Nullable
    @Override
    synchronized public UserCommand convert(User user) {
        if (user == null) {
            return null;
        }

        final UserCommand userCommand = new UserCommand();
        userCommand.setId(user.getId());
        userCommand.setName(user.getName());
        userCommand.setPortfolio(portfolioConverter.convert(user.getPortfolio()));
        if (user.getTransactions() != null && user.getTransactions().size() > 0){
            user.getTransactions()
                    .forEach( transaction -> userCommand.getTransactions().add(transactionConverter.convert(transaction)));
        }
        return userCommand;
    }
}
