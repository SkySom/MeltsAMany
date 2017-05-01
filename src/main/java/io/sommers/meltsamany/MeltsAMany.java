package io.sommers.meltsamany;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import minetweaker.MineTweakerAPI;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

import java.util.List;
import java.util.Map;

@Mod(modid = MeltsAMany.MOD_ID, name = MeltsAMany.MOD_NAME, version = MeltsAMany.VERSION, dependencies = MeltsAMany.DEPENDS)
public class MeltsAMany {

    public static final String MOD_ID = "meltaamany";
    public static final String MOD_NAME = "MeltsAMany";
    public static final String VERSION = "1.0";
    public static final String DEPENDS = "required-after:MineTweaker3;required-after:tconstruct";

    @Instance(MOD_ID)
    public static MeltsAMany instance;

    private Map<String, List<FluidStack>> meltEntries = Maps.newHashMap();
    private List<IMeltFunction> meltFunctions = Lists.newArrayList();

    @EventHandler
    public void init(FMLInitializationEvent event) {
        MineTweakerAPI.registerClass(MeltRegistry.class);
        MinecraftForge.EVENT_BUS.register(new MeltHandler());
    }

    public Map<String, List<FluidStack>> getMeltEntries() {
        return meltEntries;
    }

    public List<IMeltFunction> getMeltFunctions() {
        return meltFunctions;
    }
}
