package com.example.Aktien_Rechner.bootstrap;

import com.example.Aktien_Rechner.domain.User;
import com.example.Aktien_Rechner.repository.ShareRepository;
import com.example.Aktien_Rechner.repository.UserRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

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
    }

    private List<User> getUsers(){
        List<User> users = new ArrayList<>(3);

       // Optional<>

        return users;
    }
}
