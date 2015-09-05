package tehnut.soulshards;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import tehnut.soulshards.compat.waila.CompatWaila;
import tehnut.soulshards.enumeration.EnumTier;
import tehnut.soulshards.proxy.CommonProxy;
import tehnut.soulshards.registry.*;
import tehnut.soulshards.util.EntityMapper;
import tehnut.soulshards.util.Utils;
import tehnut.soulshards.util.handler.EventHandler;
import tehnut.soulshards.util.helper.ShardHelper;

import java.io.File;

@Mod(modid = ModInformation.ID, name = ModInformation.NAME, version = ModInformation.VERSION)
public class SoulShardsRespawn {

    @Mod.Instance
    public static SoulShardsRespawn instance;

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

        EventHandler eventHandler = new EventHandler();
        FMLCommonHandler.instance().bus().register(eventHandler);
        MinecraftForge.EVENT_BUS.register(eventHandler);

        SpawnableRegistry.registerSpawnables();
        BlockRegistry.registerBlocks();
        ItemRegistry.registerItems();
        EnchantmentRegistry.registerEnchantments();
        RecipeRegistry.registerSoulForgeRecipes();
        RecipeRegistry.registerSoulForgeFuels();

        Utils.registerCompat(CompatWaila.class, "Waila");
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {

    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {

    }

    @Mod.EventHandler
    public void serverStarting(FMLServerStartingEvent event) {
        proxy.registerCommands();
    }
}
