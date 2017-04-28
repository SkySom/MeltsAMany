package io.sommers.meltsamany;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import slimeknights.tconstruct.library.smeltery.SmelteryTank;
import slimeknights.tconstruct.smeltery.events.TinkerSmelteryEvent;

public class MeltHandler {
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onMelting(TinkerSmelteryEvent.OnMelting onMeltingEvent) {
        for (MeltEntry meltEntry : MeltsAMany.instance.meltEntryList) {
            if (ItemStack.areItemStacksEqual(meltEntry.getItemStack(), onMeltingEvent.itemStack)) {
                FluidStack additional = meltEntry.getFluidStack().copy();
                SmelteryTank tank = onMeltingEvent.smeltery.getTank();
                int currentAvailable = tank.getCapacity() - tank.getFluidAmount();
                currentAvailable -= onMeltingEvent.result.amount;
                if (additional.amount > currentAvailable) {
                    additional.amount = currentAvailable;
                }
                tank.fill(additional, true);
            }
        }
    }
}
