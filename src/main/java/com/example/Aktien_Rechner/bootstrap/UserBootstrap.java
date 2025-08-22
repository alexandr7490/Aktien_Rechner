package com.example.Aktien_Rechner.bootstrap;

import com.example.Aktien_Rechner.domain.*;
import com.example.Aktien_Rechner.repository.ShareRepository;
import com.example.Aktien_Rechner.repository.UserRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class UserBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private final ShareRepository shareRepository;
    private final UserRepository userRepository;

    public UserBootstrap(ShareRepository shareRepository, UserRepository userRepository) {
        this.shareRepository = shareRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        shareRepository.saveAll(getShares());
        userRepository.saveAll(getUsers());
    }

    private List<Share> getShares() {

        List<Share> shares = new ArrayList<>(4);

        Share share1=new Share();
        share1.setName("Siemens");
        share1.setShortName("SIE");
        share1.setActualPrice( new BigDecimal(100));
        shares.add(share1);

        Share share2 = new Share();
        share2.setName("Apple");
        share2.setShortName("AAPL");
        share2.setActualPrice( new BigDecimal(190));
        shares.add(share2);

        Share share3=new Share();
        share3.setName("Microsoft");
        share3.setShortName("MCR");
        share3.setActualPrice( new BigDecimal(350));
        shares.add(share3);

        Share share4=new Share();
        share4.setName("Schneider");
        share4.setShortName("SCH");
        share4.setActualPrice( new BigDecimal(225));
        shares.add(share4);

        return shares;
    }

    private List<User> getUsers(){



        List<User> users = new ArrayList<>(3);

        User user1=new User();
        user1.setName("User1");
        Portfolio portfolio1_user1 = new Portfolio();

        List<Holding> holdings_user1 = new ArrayList<>(2);
        Holding holding1_user1 = new Holding();
        Holding holding2_user1 = new Holding();

        holding1_user1.setPortfolio(portfolio1_user1);
        holding2_user1.setPortfolio(portfolio1_user1);
        holding1_user1.setShare(shareRepository.findByName("Siemens"));
        holding1_user1.setQuantity(5);
        holding2_user1.setShare(shareRepository.findByName("Apple"));
        holding2_user1.setQuantity(10);

        holdings_user1.add(holding1_user1);
        holdings_user1.add(holding2_user1);

        portfolio1_user1.setUser(user1);
        portfolio1_user1.setHoldings(holdings_user1);

        user1.setPortfolio(portfolio1_user1);

        List<Transaction> transaction_user1 = new ArrayList<>(3);

        Transaction trans1_user1 = new Transaction();
        trans1_user1.setUser(user1);
        trans1_user1.setQuantity(5);
        trans1_user1.setPrice(50);
        trans1_user1.setType(TransactionType.BUY);
        trans1_user1.setTimestamp(LocalDateTime.of(2016, 9, 5, 12, 30));
        trans1_user1.setShare(shareRepository.findByName("Siemens"));
        transaction_user1.add(trans1_user1);

        Transaction trans2_user1 = new Transaction();
        trans2_user1.setUser(user1);
        trans2_user1.setQuantity(5);
        trans2_user1.setPrice(700);
        trans2_user1.setType(TransactionType.BUY);
        trans2_user1.setTimestamp(LocalDateTime.of(2019, 9, 5, 12, 30));
        trans2_user1.setShare(shareRepository.findByName("Apple"));
        transaction_user1.add(trans2_user1);

        Transaction trans3_user1 = new Transaction();
        trans3_user1.setUser(user1);
        trans3_user1.setQuantity(5);
        trans3_user1.setPrice(80);
        trans3_user1.setType(TransactionType.BUY);
        trans3_user1.setTimestamp(LocalDateTime.of(2020, 1, 1, 12, 30));
        trans3_user1.setShare(shareRepository.findByName("Apple"));
        transaction_user1.add(trans3_user1);

        user1.setTransactions(transaction_user1);

        users.add(user1);
//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%_ENDE USER1

        User user2 = new User();
        user2.setName("User2");
        Portfolio portfolio1_user2 = new Portfolio();

        List<Holding> holdings_user2 = new ArrayList<>(2);
        Holding holding1_user2 = new Holding();
        Holding holding2_user2 = new Holding();

        holding1_user2.setPortfolio(portfolio1_user2);
        holding2_user2.setPortfolio(portfolio1_user2);
        holding1_user2.setShare(shareRepository.findByName("Apple"));
        holding1_user2.setQuantity(12);
        holding2_user2.setShare(shareRepository.findByName("Microsoft"));
        holding2_user2.setQuantity(14);

        holdings_user2.add(holding1_user2);
        holdings_user2.add(holding2_user2);

        portfolio1_user2.setUser(user2);
        portfolio1_user2.setHoldings(holdings_user2);

        user2.setPortfolio(portfolio1_user2);

        List<Transaction> transaction_user2 = new ArrayList<>(3);

        Transaction trans1_user2 = new Transaction();
        trans1_user2.setUser(user2);
        trans1_user2.setQuantity(10);
        trans1_user2.setPrice(30);
        trans1_user2.setType(TransactionType.BUY);
        trans1_user2.setTimestamp(LocalDateTime.of(2016, 9, 5, 12, 30));
        trans1_user2.setShare(shareRepository.findByName("Apple"));
        transaction_user2.add(trans1_user2);

        Transaction trans2_user2 = new Transaction();
        trans2_user2.setUser(user2);
        trans2_user2.setQuantity(2);
        trans2_user2.setPrice(70);
        trans2_user2.setType(TransactionType.BUY);
        trans2_user2.setTimestamp(LocalDateTime.of(2020, 2, 1, 12, 30,11));
        trans2_user2.setShare(shareRepository.findByName("Apple"));
        transaction_user2.add(trans2_user2);

        Transaction trans3_user2 = new Transaction();
        trans3_user2.setUser(user2);
        trans3_user2.setQuantity(14);
        trans3_user2.setPrice(80);
        trans3_user2.setType(TransactionType.BUY);
        trans3_user2.setTimestamp(LocalDateTime.of(2010, 1, 1, 12, 30));
        trans3_user2.setShare(shareRepository.findByName("Microsoft"));
        transaction_user2.add(trans3_user2);

        user2.setTransactions(transaction_user2);

        users.add(user2);
//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%_ENDE USER2

        User user3=new User();
        user3.setName("User3");
        Portfolio portfolio1_user3 = new Portfolio();

        List<Holding> holdings_user3 = new ArrayList<>(2);
        Holding holding1_user3 = new Holding();
        Holding holding2_user3 = new Holding();

        holding1_user3.setPortfolio(portfolio1_user3);
        holding2_user3.setPortfolio(portfolio1_user3);
        holding1_user3.setShare(shareRepository.findByName("Siemens"));
        holding1_user3.setQuantity(20);
        holding2_user3.setShare(shareRepository.findByName("Schneider"));
        holding2_user3.setQuantity(100);

        holdings_user3.add(holding1_user3);
        holdings_user3.add(holding2_user3);

        portfolio1_user3.setUser(user3);
        portfolio1_user3.setHoldings(holdings_user3);

        user3.setPortfolio(portfolio1_user3);

        List<Transaction> transaction_user3 = new ArrayList<>(2);

        Transaction trans1_user3 = new Transaction();
        trans1_user3.setUser(user3);
        trans1_user3.setQuantity(20);
        trans1_user3.setPrice(150);
        trans1_user3.setType(TransactionType.BUY);
        trans1_user3.setTimestamp(LocalDateTime.of(2006, 9, 5, 12, 30));
        trans1_user3.setShare(shareRepository.findByName("Siemens"));
        transaction_user3.add(trans1_user3);

        Transaction trans2_user3 = new Transaction();
        trans2_user3.setUser(user3);
        trans2_user3.setQuantity(100);
        trans2_user3.setPrice(50);
        trans2_user3.setType(TransactionType.BUY);
        trans2_user3.setTimestamp(LocalDateTime.of(2022, 9, 5, 12, 30));
        trans2_user3.setShare(shareRepository.findByName("Schneider"));
        transaction_user3.add(trans2_user3);

        //user3.setTransactions(transaction_user3);
        users.add(user3);

        return users;
    }
}
