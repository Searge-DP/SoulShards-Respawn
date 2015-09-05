package tehnut.soulshards.block;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import tehnut.soulshards.ModInformation;
import tehnut.soulshards.SoulShardsRespawn;
import tehnut.soulshards.enumeration.EnumTier;
import tehnut.soulshards.registry.ItemRegistry;
import tehnut.soulshards.tile.TileEntitySpawner;
import tehnut.soulshards.util.helper.ShardHelper;

public class BlockSpawner extends BlockContainer {

    public BlockSpawner() {
        super(Material.iron);

        setBlockName(ModInformation.UNLOC + ".spawner");
        setBlockTextureName("minecraft:mob_spawner");
        setCreativeTab(SoulShardsRespawn.tabSoulShards);
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
        TileEntity tile = world.getTileEntity(x, y, z);

        if (tile instanceof TileEntitySpawner) {
            TileEntitySpawner spawner = (TileEntitySpawner) tile;
            ItemStack held = player.getHeldItem();

            if (held != null) {
                if (held.getItem() == ItemRegistry.shard) {
                    if (ShardHelper.getTierFromShard(held) != EnumTier.UNBOUND && ShardHelper.getTierFromShard(held) != EnumTier.ZERO && ShardHelper.hasEntity(held)) {
                        if (spawner.getStackInSlot(0) == null) {
                            player.inventory.decrStackSize(player.inventory.currentItem, 1);
                            spawner.setInventorySlotContents(0, held);
                            return true;
                        }
                    }
                }
            } else if (player.isSneaking()) {
                if (spawner.getStackInSlot(0) != null) {
                    player.inventory.addItemStackToInventory(spawner.getStackInSlot(0));
                    spawner.setInventorySlotContents(0, null);
                }
            }
        }

        return false;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileEntitySpawner();
    }
}
