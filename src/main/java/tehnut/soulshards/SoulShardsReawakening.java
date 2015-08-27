package tehnut.soulshards;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import tehnut.soulshards.enumeration.EnumTier;
import tehnut.soulshards.proxy.CommonProxy;
import tehnut.soulshards.registry.BlockRegistry;
import tehnut.soulshards.registry.ItemRegistry;
import tehnut.soulshards.util.helper.ShardHelper;

import java.io.File;

@Mod(modid = ModInformation.ID, name = ModInformation.NAME, version = ModInformation.VERSION)
public class SoulShardsReawakening {

    @Mod.Instance
    public static SoulShardsReawakening instance;

    @SidedProxy(clientSide = ModInformation.CLIENTPROXY, serverSide = ModInformation.COMMONPROXY)
    public static CommonProxy proxy;

    public static CreativeTabs tabSoulShards = new CreativeTabs(ModInformation.UNLOC + ".creativeTab") {
        @Override
        public ItemStack getIconItemStack() {
            return ShardHelper.setTierForShard(new ItemStack(ItemRegistry.shard), EnumTier.FIVE);
        }

        @Override
        public Item getTabIconItem() {
            return ItemRegistry.shard;
        }
    };

    private static File configDir;

    public static File getConfigDir() {
        return configDir;
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        configDir = event.getModConfigurationDirectory();
        configDir.mkdirs();
        ConfigHandler.init(new File(configDir.getPath(), ModInformation.ID + ".cfg"));

        BlockRegistry.registerBlocks();
        ItemRegistry.registerItems();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {

    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {

    }
}
