package tehnut.soulshards.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import tehnut.soulshards.ModInformation;
import tehnut.soulshards.SoulShardsRespawn;
import tehnut.soulshards.tile.TileEntitySoulForge;

public class BlockSoulForge extends BlockContainer {

    private IIcon frontIdle;
    private IIcon frontActive;
    private IIcon bottom;
    private IIcon top;

    public boolean active;

    public BlockSoulForge() {
        super(Material.rock);

        setBlockName(ModInformation.UNLOC + ".soul.forge");
        setBlockTextureName("furnace_top");
        setCreativeTab(SoulShardsRespawn.tabSoulShards);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister ir) {
        this.frontIdle = ir.registerIcon(ModInformation.DOMAIN + "forge_front_idle");
        this.frontActive = ir.registerIcon(ModInformation.DOMAIN + "forge_front_active");
        this.blockIcon = ir.registerIcon(ModInformation.DOMAIN + "forge_side");
        this.bottom = ir.registerIcon("obsidian");
        this.top = ir.registerIcon("furnace_top");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        if (side == 1)
            return this.top;
        else if (side == 0)
            return this.bottom;
        else if (side != meta)
            return this.blockIcon;
        else
            return active ? frontActive : frontIdle;
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack stack) {
        int dir = MathHelper.floor_double((double) (player.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;

        switch (dir) {
            case 0: world.setBlockMetadataWithNotify(x, y, z, 2, 2); break;
            case 1: world.setBlockMetadataWithNotify(x, y, z, 5, 2); break;
            case 2: world.setBlockMetadataWithNotify(x, y, z, 3, 2); break;
            case 3: world.setBlockMetadataWithNotify(x, y, z, 4, 2); break;
            default: world.setBlockMetadataWithNotify(x, y, z, 2, 2); break;
        }
    }

    public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z, EntityPlayer player) {
        return new ItemStack(this, 1, 3);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileEntitySoulForge();
    }
}
