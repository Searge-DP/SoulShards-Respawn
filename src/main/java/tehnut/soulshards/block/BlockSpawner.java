package tehnut.soulshards.block;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;
import tehnut.soulshards.ModInformation;
import tehnut.soulshards.enumeration.EnumTier;
import tehnut.soulshards.registry.ItemRegistry;
import tehnut.soulshards.tile.TileEntitySpawner;
import tehnut.soulshards.util.helper.ShardHelper;

public class BlockSpawner extends BlockContainer {

    public BlockSpawner() {
        super(Material.iron);

        setBlockName(ModInformation.ID + ".spawner");
        setBlockTextureName("minecraft:mob_spawner");
        setCreativeTab(CreativeTabs.tabTools);
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
        TileEntity tile = world.getTileEntity(x, y, z);

        if (tile instanceof TileEntitySpawner) {
            TileEntitySpawner spawner = (TileEntitySpawner) tile;
            EnumTier tier = spawner.getShardTier();

            if (player.getHeldItem() == null) {
                if (player.isSneaking() && !world.isRemote) {
                    if (tier != EnumTier.DEFAULT) {
                        player.inventory.addItemStackToInventory(spawner.getShardIn());
                        spawner.resetTier();
                    }
                }
            } else if (player.getHeldItem().getItem() == ItemRegistry.shard) {
                if (spawner.getShardTier() == EnumTier.DEFAULT) {
                    if (!world.isRemote) {
                        spawner.setShardTier(ShardHelper.getTierFromShard(player.getHeldItem()));
                        spawner.setShardIn(player.getHeldItem());
                        player.inventory.decrStackSize(player.inventory.currentItem, 1);
                    }
                    player.swingItem();
                }
            } else if (player.getHeldItem().getItem() == Items.diamond_hoe) {
                if (!world.isRemote) {
                    player.addChatComponentMessage(new ChatComponentText("Tier " + spawner.getShardTier()));
                    player.addChatComponentMessage(new ChatComponentText("Item " + spawner.getShardIn().getDisplayName()));
                    player.addChatComponentMessage(new ChatComponentText("Item-Kills " + ShardHelper.getKillsFromShard(spawner.getShardIn())));
                    player.addChatComponentMessage(new ChatComponentText("Item-Tier " + ShardHelper.getTierFromShard(spawner.getShardIn())));
                    player.addChatComponentMessage(new ChatComponentText("Item-Entity " + ShardHelper.getShardEntity(spawner.getShardIn())));
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

//    @Override
//    public int getRenderType() {
//        return BanterTokens.renderIDSpawner;
//    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileEntitySpawner();
    }
}
