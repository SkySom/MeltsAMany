package io.sommers.meltsamany;

import com.google.common.collect.Lists;
import minetweaker.api.item.IItemStack;
import minetweaker.api.liquid.ILiquidStack;
import minetweaker.mc1102.item.MCItemStack;
import minetweaker.mc1102.liquid.MCLiquidStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import slimeknights.tconstruct.library.smeltery.SmelteryTank;
import slimeknights.tconstruct.smeltery.events.TinkerSmelteryEvent;

import java.util.Iterator;
import java.util.List;

public class MeltHandler {
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onMelting(TinkerSmelteryEvent.OnMelting onMeltingEvent) {
        String itemStackString = onMeltingEvent.itemStack.toString();
        SmelteryTank tank = onMeltingEvent.smeltery.getTank();
        int currentAvailable = tank.getCapacity() - tank.getFluidAmount();
        currentAvailable -= onMeltingEvent.result.amount;
        if (MeltsAMany.instance.getMeltEntries().containsKey(itemStackString)) {
            Iterator<FluidStack> additional = MeltsAMany.instance.getMeltEntries().get(itemStackString).iterator();
            while (additional.hasNext() && currentAvailable > 0) {
                currentAvailable = fillTank(additional.next(), tank, currentAvailable);
            }
        }

        if (currentAvailable > 0) {
            IItemStack itemStack = new MCItemStack(onMeltingEvent.itemStack.copy());
            ILiquidStack output = new MCLiquidStack(onMeltingEvent.result.copy());
            ILiquidStack fuel = new MCLiquidStack(onMeltingEvent.smeltery.currentFuel.copy());
            List<ILiquidStack> returned = Lists.newArrayList();
            for (IMeltFunction meltFunction : MeltsAMany.instance.getMeltFunctions()) {
                returned.addAll(meltFunction.onMelt(itemStack, output, fuel));
            }
            Iterator<ILiquidStack> returnedIterator = returned.iterator();
            while (returnedIterator.hasNext() && currentAvailable > 0) {
                currentAvailable = fillTank(returnedIterator.next(), tank, currentAvailable);
            }
        }
    }

    private int fillTank(ILiquidStack liquidStack, SmelteryTank smelteryTank, int currentAvailable) {
        if (liquidStack.getInternal() instanceof FluidStack) {
            currentAvailable = fillTank((ILiquidStack) liquidStack.getInternal(), smelteryTank, currentAvailable);
        }
        return currentAvailable;
    }

    private int fillTank(FluidStack currentStack, SmelteryTank smelteryTank, int currentAvailable) {
        if (currentStack.amount > currentAvailable) {
            currentStack.amount = currentAvailable;
        }
        if (currentStack.amount >= 0) {
            currentAvailable -= currentStack.amount;
            smelteryTank.fill(currentStack, true);
        }
        return currentAvailable;
    }
}
