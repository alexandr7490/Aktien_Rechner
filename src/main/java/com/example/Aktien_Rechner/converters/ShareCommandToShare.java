package com.example.Aktien_Rechner.converters;

import com.example.Aktien_Rechner.commands.ShareCommand;
import com.example.Aktien_Rechner.domain.Share;
import jakarta.annotation.Nullable;
import org.springframework.core.convert.converter.Converter;

public class ShareCommandToShare  implements Converter<ShareCommand, Share> {

    private final OrderCommandToOrder orderConverter;

    public ShareCommandToShare(OrderCommandToOrder orderConverter) {
        this.orderConverter = orderConverter;
    }

    @Nullable
    @Override
    public Share convert(ShareCommand shareCommand) {
        if (shareCommand == null) {
            return null;
        }
        Share share = new Share();
        share.setId(shareCommand.getId());
        share.setName(shareCommand.getName());
        share.setShortName(shareCommand.getShortName());
        share.setActualPrice(shareCommand.getActualPrice());
        share.setOrder(orderConverter.convert(shareCommand.getOrderCommand()));

        return share;
    }
}
