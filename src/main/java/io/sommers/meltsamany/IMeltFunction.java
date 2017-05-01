package io.sommers.meltsamany;

import minetweaker.api.item.IItemStack;
import minetweaker.api.liquid.ILiquidStack;

import java.util.List;

public interface IMeltFunction {
    List<ILiquidStack> onMelt(IItemStack input, ILiquidStack output, ILiquidStack fuel);
}
