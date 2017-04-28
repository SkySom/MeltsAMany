package io.sommers.meltsamany;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import slimeknights.tconstruct.smeltery.events.TinkerSmelteryEvent;

public class MeltHandler {
    @SubscribeEvent
    public void onMelting(TinkerSmelteryEvent.OnMelting onMeltingEvent) {
        for (MeltEntry meltEntry : MeltsAMany.instance.meltEntryList) {
            if (ItemStack.areItemStacksEqual(meltEntry.getItemStack(), onMeltingEvent.itemStack)) {
                FluidStack additional = meltEntry.getFluidStack().copy();
                onMeltingEvent.smeltery.getTank().fill(additional, true);
            }
        }
    }
}
