package com.example.Aktien_Rechner.services;

import com.example.Aktien_Rechner.domain.Share;
import com.example.Aktien_Rechner.repository.ShareRepository;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.Set;

@Service
public class ShareServiceImpl implements ShareService{
    private final ShareRepository shareRepository;

    public ShareServiceImpl(ShareRepository shareRepository) {
        this.shareRepository = shareRepository;
    }

    @Override
    public Share findById(long id) {
        return null;
    }

    @Override
    public Set<Share> getShares() {
        return Set.of();
    }

    @Override
    public void deleteById(long id) {

    }

    @Override
    public double getActualPrice(Date time) {
        return 0;
    }

    @Override
    public double getPriceAtTime(Date time) {
        return 0;
    }
}
