package com.example.Aktien_Rechner.converters;

import com.example.Aktien_Rechner.commands.HoldingCommand;
import com.example.Aktien_Rechner.domain.Holding;
import jakarta.annotation.Nullable;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class HoldingCommandToHolding implements Converter<HoldingCommand, Holding> {
    private final ShareCommandToShare shareConverter;

    public HoldingCommandToHolding(ShareCommandToShare shareConverter) {
        this.shareConverter = shareConverter;
    }

    @Nullable
    @Override
    synchronized public Holding convert(HoldingCommand holdingCommand) {
        if (holdingCommand == null) {
            return null;
        }

        final Holding holding = new Holding();
        holding.setId(holdingCommand.getId());
        holding.setQuantity(holdingCommand.getQuantity());
        holding.setShare(shareConverter.convert(holdingCommand.getShare()));

        return holding;
    }

}
