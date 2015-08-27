package tehnut.soulshards;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import tehnut.soulshards.proxy.CommonProxy;

import java.io.File;

@Mod(modid = ModInformation.ID, name = ModInformation.NAME, version = ModInformation.VERSION)
public class SoulShardsReawakening {

    @Mod.Instance
    public static SoulShardsReawakening instance;

    @SidedProxy(clientSide = ModInformation.CLIENTPROXY, serverSide = ModInformation.COMMONPROXY)
    public static CommonProxy proxy;

    private static File configDir;

    public static File getConfigDir() {
        return configDir;
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        configDir = event.getModConfigurationDirectory();
        configDir.mkdirs();
        ConfigHandler.init(new File(configDir.getPath(), ModInformation.ID + ".cfg"));

    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {

    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {

    }
}
