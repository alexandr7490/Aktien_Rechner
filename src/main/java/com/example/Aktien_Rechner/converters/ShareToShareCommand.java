package com.example.Aktien_Rechner.converters;

import com.example.Aktien_Rechner.commands.ShareCommand;
import com.example.Aktien_Rechner.domain.Order;
import com.example.Aktien_Rechner.domain.Share;
import jakarta.annotation.Nullable;
import org.springframework.core.convert.converter.Converter;

public class ShareToShareCommand  implements Converter<Share, ShareCommand> {
private final OrderToOrderCommand orderConverter;

    public ShareToShareCommand(OrderToOrderCommand orderToOrderCommand) {
        this.orderConverter = orderToOrderCommand;
    }

    @Nullable
    @Override
    public ShareCommand convert(Share share) {
        if (share == null) {
            return null;
        }

        ShareCommand shareCommand = new ShareCommand();
        shareCommand.setId(share.getId());
        shareCommand.setName(share.getName());
        shareCommand.setShortName(share.getShortName());
        shareCommand.setActualPrice(share.getActualPrice());
        shareCommand.setOrderCommand(orderConverter.convert(share.getOrder()));

        return shareCommand;
    }
}
