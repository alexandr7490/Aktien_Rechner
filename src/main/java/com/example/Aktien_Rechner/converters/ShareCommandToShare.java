package com.example.Aktien_Rechner.converters;

import com.example.Aktien_Rechner.commands.ShareCommand;
import com.example.Aktien_Rechner.domain.Share;
import jakarta.annotation.Nullable;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ShareCommandToShare implements Converter<ShareCommand, Share> {
    @Nullable
    @Override
    synchronized public Share convert(ShareCommand shareCommand) {
        if (shareCommand == null) {
            return null;
        }

        final Share share = new Share();
        share.setId(shareCommand.getId());
        share.setName(shareCommand.getName());
        share.setShortName(shareCommand.getShortName());
        share.setActualPrice(shareCommand.getActualPrice());
        return share;
    }
}
