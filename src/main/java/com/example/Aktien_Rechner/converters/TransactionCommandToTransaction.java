package com.example.Aktien_Rechner.converters;

import com.example.Aktien_Rechner.commands.TransactionCommand;
import com.example.Aktien_Rechner.domain.Transaction;
import jakarta.annotation.Nullable;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class TransactionCommandToTransaction implements Converter<TransactionCommand, Transaction> {

    private final ShareCommandToShare shareConverter;
    private final UserCommandToUser userConverter;

    public TransactionCommandToTransaction(ShareCommandToShare shareConverter, UserCommandToUser userConverter) {
        this.shareConverter = shareConverter;
        this.userConverter = userConverter;
    }


    @Nullable
    @Override
    synchronized public Transaction convert(TransactionCommand transactionCommand) {
        if (transactionCommand == null) {
            return null;
        }

        final Transaction transaction = new Transaction();
        transaction.setId(transactionCommand.getId());
        transaction.setQuantity(transactionCommand.getQuantity());
        transaction.setPrice(transactionCommand.getPrice());
        transaction.setTimestamp(transactionCommand.getTimestamp());
        transaction.setType(transactionCommand.getType());
        transaction.setUser(userConverter.convert(transactionCommand.getUser()));
        transaction.setShare(shareConverter.convert(transactionCommand.getShare()));

        return transaction;
    }
}
