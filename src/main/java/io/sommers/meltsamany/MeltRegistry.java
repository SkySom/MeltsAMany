package io.sommers.meltsamany;

import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IItemStack;
import minetweaker.api.liquid.ILiquidStack;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.meltsamany.MeltRegistry")
public class MeltRegistry {
    @ZenMethod
    public static void addSecondaryResult(IItemStack itemStackInput, ILiquidStack liquidStack) {
        ItemStack inputStack = null;
        FluidStack fluidStack = null;

        if (itemStackInput != null && itemStackInput.getInternal() instanceof ItemStack) {
            inputStack = (ItemStack) itemStackInput.getInternal();
        } else {
            MineTweakerAPI.getLogger().logError("Couldn't find ItemStack input");
        }

        if (liquidStack != null && liquidStack.getInternal() instanceof FluidStack) {
            fluidStack = (FluidStack) liquidStack.getInternal();
        } else {
            MineTweakerAPI.getLogger().logError("Couldn't find FluidStack output");
        }

        if (fluidStack != null && inputStack != null) {
            MeltsAMany.instance.meltEntryList.add(new MeltEntry(inputStack, fluidStack));
        }
    }
}
