package com.example.Aktien_Rechner.converters;

import com.example.Aktien_Rechner.commands.ShareCommand;
import com.example.Aktien_Rechner.domain.Share;
import jakarta.annotation.Nullable;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ShareToShareCommand implements Converter<Share, ShareCommand> {
    @Nullable
    @Override
    synchronized public ShareCommand convert(Share share) {
        if (share == null) {
            return null;
        }

        final ShareCommand shareCommand = new ShareCommand();
        shareCommand.setId(share.getId());
        shareCommand.setName(share.getName());
        shareCommand.setShortName(share.getShortName());
        shareCommand.setActualPrice(share.getActualPrice());
        return shareCommand;
    }
}
