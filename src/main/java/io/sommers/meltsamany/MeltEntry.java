package io.sommers.meltsamany;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class MeltEntry {
    private ItemStack itemStack;
    private FluidStack fluidStack;

    public MeltEntry(ItemStack internal, FluidStack fluidStack) {
        this.itemStack = internal;
        this.fluidStack = fluidStack;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public void setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public FluidStack getFluidStack() {
        return fluidStack;
    }

    public void setFluidStack(FluidStack fluidStack) {
        this.fluidStack = fluidStack;
    }
}
