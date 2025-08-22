package com.example.Aktien_Rechner.services;

import com.example.Aktien_Rechner.commands.UserCommand;
import com.example.Aktien_Rechner.domain.Transaction;
import com.example.Aktien_Rechner.domain.User;

import java.util.List;

public interface UserService {

    UserCommand saveUserCommand(UserCommand userCommand); //Используй для добавления и обновления объектов!

    User findById(Long id);
    List<Transaction> getLastNTransactions(Long userId, Integer amount);

}
