package com.example.Aktien_Rechner.repository;

import com.example.Aktien_Rechner.domain.Share;
import com.example.Aktien_Rechner.domain.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ShareRepository extends CrudRepository<Share, Long> {

    Optional<Share> findByName(String name);

    Optional<Share> findByShortName(String shortName);
}
