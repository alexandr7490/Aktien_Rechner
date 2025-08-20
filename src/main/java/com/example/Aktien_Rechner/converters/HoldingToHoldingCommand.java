package com.example.Aktien_Rechner.converters;

import com.example.Aktien_Rechner.commands.HoldingCommand;
import com.example.Aktien_Rechner.domain.Holding;
import jakarta.annotation.Nullable;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class HoldingToHoldingCommand implements Converter<Holding, HoldingCommand> {

    private final ShareToShareCommand shareConverter;

    public HoldingToHoldingCommand(ShareToShareCommand shareConverter) {
        this.shareConverter = shareConverter;
    }

    @Nullable
    @Override
    synchronized public HoldingCommand convert(Holding holding) {
        if (holding == null) {
            return null;
        }

        final HoldingCommand holdingCommand = new HoldingCommand();
        holdingCommand.setId(holding.getId());
        holdingCommand.setQuantity(holding.getQuantity());
        holdingCommand.setShare(shareConverter.convert(holding.getShare()));

        return holdingCommand;
    }

}
