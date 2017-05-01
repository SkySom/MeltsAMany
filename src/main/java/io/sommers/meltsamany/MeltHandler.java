package io.sommers.meltsamany;

import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import slimeknights.tconstruct.library.smeltery.SmelteryTank;
import slimeknights.tconstruct.smeltery.events.TinkerSmelteryEvent;

public class MeltHandler {
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onMelting(TinkerSmelteryEvent.OnMelting onMeltingEvent) {
        String itemStackString = onMeltingEvent.itemStack.toString();
        if (MeltsAMany.instance.getMeltEntries().containsKey(itemStackString)) {
            FluidStack[] additional = MeltsAMany.instance.getMeltEntries().get(itemStackString);
            SmelteryTank tank = onMeltingEvent.smeltery.getTank();
            int currentAvailable = tank.getCapacity() - tank.getFluidAmount();
            currentAvailable -= onMeltingEvent.result.amount;
            for (FluidStack fluidStack : additional) {
                FluidStack copy = fluidStack.copy();
                if (copy.amount > currentAvailable) {
                    copy.amount = currentAvailable;
                }
                if (copy.amount >= 0) {
                    currentAvailable -= copy.amount;
                    tank.fill(copy, true);
                }
            }
        }
    }
}
