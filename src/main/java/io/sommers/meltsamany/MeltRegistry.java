package io.sommers.meltsamany;

import minetweaker.api.item.IItemStack;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.meltsamany.MeltRegistry")
public class MeltRegistry {
    @ZenMethod
    public static void addSecondaryResult(IItemStack itemStackInput, String outputFluidName, int outputAmount) {
        Fluid fluid = FluidRegistry.getFluid(outputFluidName);
        if (fluid != null) {
            FluidStack fluidStack = new FluidStack(fluid, outputAmount);
            if (itemStackInput.getInternal() instanceof ItemStack) {
                MeltsAMany.instance.meltEntryList.add(new MeltEntry((ItemStack) itemStackInput.getInternal(), fluidStack));
            }
        }
    }
}
