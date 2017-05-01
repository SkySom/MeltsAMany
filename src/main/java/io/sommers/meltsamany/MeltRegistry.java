package io.sommers.meltsamany;

import com.google.common.collect.Lists;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IIngredient;
import minetweaker.api.item.IItemStack;
import minetweaker.api.liquid.ILiquidStack;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.List;

@ZenClass("mods.meltsamany.MeltRegistry")
public class MeltRegistry {
    @ZenMethod
    public static void addSecondaryResult(IIngredient itemStackInput, ILiquidStack... liquidStacks) {
        List<ItemStack> inputStack = null;
        FluidStack[] fluidStacks = null;


        if (itemStackInput != null) {
            inputStack = Lists.newArrayList();
            for (IItemStack itemStack : itemStackInput.getItems()) {
                if (itemStack.getInternal() instanceof ItemStack) {
                    inputStack.add((ItemStack) itemStack.getInternal());
                }
            }
        } else {
            MineTweakerAPI.getLogger().logError("You must enter an IIngredient");
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
            for (ItemStack itemStack : inputStack) {
                MeltsAMany.instance.getMeltEntries().put(itemStack.toString(), fluidStacks);
            }
        }
    }
}
