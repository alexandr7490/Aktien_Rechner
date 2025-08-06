package com.example.Aktien_Rechner.repository;

import com.example.Aktien_Rechner.domain.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByName(String name);
}
