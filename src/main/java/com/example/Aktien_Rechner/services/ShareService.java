package com.example.Aktien_Rechner.services;

import com.example.Aktien_Rechner.domain.Share;

import java.sql.Date;
import java.util.Set;

public interface ShareService {

    Share findById(long id);

    Set<Share> getShares();

    void deleteById(long id);

    double getActualPrice(Date time);

    double getPriceAtTime(Date time);
}
