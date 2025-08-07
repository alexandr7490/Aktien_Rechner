package com.example.Aktien_Rechner.converters;

import com.example.Aktien_Rechner.commands.TransactionCommand;
import com.example.Aktien_Rechner.domain.Share;
import com.example.Aktien_Rechner.domain.Transaction;
import com.example.Aktien_Rechner.domain.TransactionType;
import com.example.Aktien_Rechner.domain.User;
import jakarta.annotation.Nullable;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.sql.Date;

@Component
public class TransactionToTransactionCommand implements Converter<Transaction, TransactionCommand> {

    private final ShareToShareCommand shareConverter;
    private final UserToUserCommand userConverter;

    public TransactionToTransactionCommand(ShareToShareCommand shareConverter, UserToUserCommand userConverter) {
        this.shareConverter = shareConverter;
        this.userConverter = userConverter;
    }


    @Nullable
    @Override
    synchronized public TransactionCommand convert(Transaction transaction) {
        if (transaction == null) {
            return null;
        }

        final TransactionCommand transactionCommand = new TransactionCommand();
        transactionCommand.setId(transaction.getId());
        transactionCommand.setQuantity(transaction.getQuantity());
        transactionCommand.setPrice(transaction.getPrice());
        transactionCommand.setTimestamp(transaction.getTimestamp());
        transactionCommand.setType(transaction.getType());
        transactionCommand.setUser(userConverter.convert(transaction.getUser()));
        transactionCommand.setShare(shareConverter.convert(transaction.getShare()));

        return transactionCommand;
    }

}
