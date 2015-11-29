package tehnut.soulshards.registry;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import tehnut.soulshards.ModInformation;
import tehnut.soulshards.block.BlockSoulForge;
import tehnut.soulshards.block.BlockSpawner;
import tehnut.soulshards.item.block.ItemBlockSoulForge;
import tehnut.soulshards.tile.TileEntitySoulForge;
import tehnut.soulshards.tile.TileEntitySpawner;

public class BlockRegistry {

    public static Block spawner;
    public static Block soulForge;

    public static void registerBlocks() {
        spawner = new BlockSpawner();
        GameRegistry.registerBlock(spawner, spawner.getClass().getSimpleName());
        GameRegistry.registerTileEntity(TileEntitySpawner.class, ModInformation.ID + ":" + TileEntitySpawner.class.getSimpleName());

        soulForge = new BlockSoulForge();
        GameRegistry.registerBlock(soulForge, ItemBlockSoulForge.class, soulForge.getClass().getSimpleName());
        GameRegistry.registerTileEntity(TileEntitySoulForge.class, ModInformation.ID + ":" + TileEntitySoulForge.class.getSimpleName());
    }
}
