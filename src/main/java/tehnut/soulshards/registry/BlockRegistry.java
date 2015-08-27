package tehnut.soulshards.registry;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import tehnut.soulshards.ModInformation;
import tehnut.soulshards.block.BlockSpawner;
import tehnut.soulshards.tile.TileEntitySpawner;

public class BlockRegistry {

    public static Block spawner;

    public static void registerBlocks() {
        spawner = new BlockSpawner();
        GameRegistry.registerBlock(spawner, spawner.getClass().getSimpleName());
        GameRegistry.registerTileEntity(TileEntitySpawner.class, ModInformation.ID + ":TileEntitySpawner");
    }
}
