package tehnut.soulshards.compat.waila;

import cpw.mods.fml.common.event.FMLInterModComms;
import mcp.mobius.waila.api.IWailaRegistrar;
import tehnut.soulshards.block.BlockSoulForge;
import tehnut.soulshards.block.BlockSpawner;

public class CompatWaila {

    static {
        FMLInterModComms.sendMessage("Waila", "register", "tehnut.soulshards.compat.waila.CompatWaila.callbackRegister");
    }

    public static void callbackRegister(IWailaRegistrar registrar) {
        registrar.registerStackProvider(new SpawnerDataProvider(), BlockSpawner.class);
        registrar.registerBodyProvider(new SpawnerDataProvider(), BlockSpawner.class);
        registrar.registerStackProvider(new ForgeDataProvider(), BlockSoulForge.class);
    }
}
