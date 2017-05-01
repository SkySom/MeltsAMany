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
    public static void addSecondaryResult(IItemStack itemStackInput, ILiquidStack... liquidStacks) {
        ItemStack inputStack = null;
        FluidStack[] fluidStacks = null;

        if (itemStackInput != null && itemStackInput.getInternal() instanceof ItemStack) {
            inputStack = (ItemStack) itemStackInput.getInternal();
        } else {
            MineTweakerAPI.getLogger().logError("Couldn't find ItemStack input");
        }

        if (liquidStacks != null) {
            fluidStacks = new FluidStack[liquidStacks.length];
            for (int i = 0; i < liquidStacks.length; i++) {
                ILiquidStack liquidStack = liquidStacks[i];
                if (liquidStack != null && liquidStack.getInternal() instanceof FluidStack) {
                    fluidStacks[i] = (FluidStack) liquidStack.getInternal();
                } else {
                    MineTweakerAPI.getLogger().logError("Couldn't find FluidStack output");
                }
            }
        } else {
            MineTweakerAPI.getLogger().logError("You must enter at least one ILiquidStack");

        }
        
        if (fluidStacks != null && inputStack != null) {
            MeltsAMany.instance.getMeltEntries().put(inputStack.toString(), fluidStacks);
        }
    }
}
