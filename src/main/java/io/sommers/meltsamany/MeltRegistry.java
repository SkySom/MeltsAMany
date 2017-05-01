package io.sommers.meltsamany;

import com.google.common.collect.Lists;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IIngredient;
import minetweaker.api.item.IItemStack;
import minetweaker.api.liquid.ILiquidStack;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

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
            Map<String, List<FluidStack>> entries = MeltsAMany.instance.getMeltEntries();
            for (ItemStack itemStack : inputStack) {
                String itemStackString = itemStack.toString();
                if (!entries.containsKey(itemStackString)) {
                    entries.put(itemStackString, Arrays.asList(fluidStacks));
                } else {
                    List<FluidStack> allStacks = Lists.newArrayList();
                    allStacks.addAll(entries.get(itemStackString));
                    for (FluidStack newFluidStack : fluidStacks) {
                        boolean hasStack = false;
                        for (FluidStack currentFluidStack : allStacks) {
                            if (currentFluidStack.getFluid() == newFluidStack.getFluid()) {
                                hasStack = true;
                            }
                        }
                        if (!hasStack) {
                            allStacks.add(newFluidStack);
                        }
                    }
                    entries.put(itemStackString, allStacks);
                }
            }
        }
    }

    @ZenMethod
    public static void addMeltHandler(IMeltFunction meltFunction) {
        MeltsAMany.instance.getMeltFunctions().add(meltFunction);
    }
}
