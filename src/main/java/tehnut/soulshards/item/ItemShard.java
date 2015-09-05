package tehnut.soulshards.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import tehnut.soulshards.ModInformation;
import tehnut.soulshards.SoulShardsRespawn;
import tehnut.soulshards.enumeration.EnumTier;
import tehnut.soulshards.util.helper.ShardHelper;

import java.util.List;

public class ItemShard extends Item {

    public IIcon[] icons = new IIcon[16];

    public ItemShard() {
        super();

        setUnlocalizedName(ModInformation.UNLOC + ".shard");
        setCreativeTab(SoulShardsRespawn.tabSoulShards);
        setMaxStackSize(1);
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister) {
        for (int i = 0; i < 6; i++)
            icons[i] = iconRegister.registerIcon(ModInformation.DOMAIN + "shard_tier" + i);

        icons[7] = iconRegister.registerIcon(ModInformation.DOMAIN + "shard_unbound");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(ItemStack stack, int pass) {
        if (!ShardHelper.isBound(stack))
            return icons[7];

        return icons[ShardHelper.getTierFromShard(stack).ordinal()];
    }

    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIconIndex(ItemStack stack) {
        if (!ShardHelper.isBound(stack))
            return icons[7];

        return icons[ShardHelper.getTierFromShard(stack).ordinal()];
    }

    @SideOnly(Side.CLIENT)
    @SuppressWarnings("unchecked")
    public void getSubItems(Item item, CreativeTabs tabs, List list) {
        for (EnumTier tier : EnumTier.values())
            list.add(ShardHelper.setTierForShard(new ItemStack(this), tier));
    }

    @Override
    public boolean hasEffect(ItemStack stack, int pass) {
        return ShardHelper.getTierFromShard(stack) == EnumTier.FIVE;
    }

    @SideOnly(Side.CLIENT)
    @SuppressWarnings("unchecked")
    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean advanced) {
        if (ShardHelper.isBound(stack)) {
            list.add(StatCollector.translateToLocalFormatted("tooltip.soulshards.shard.tier", ShardHelper.getTierFromShard(stack).toCasedString()));
            list.add(StatCollector.translateToLocalFormatted("tooltip.soulshards.shard.kill", ShardHelper.getKillsFromShard(stack)));
            if (ShardHelper.hasEntity(stack))
                list.add(StatCollector.translateToLocalFormatted("tooltip.soulshards.shard.entity", ShardHelper.getEntityFromShard(stack)));
        }
        else {
            list.add(StatCollector.translateToLocalFormatted("tooltip.soulshards.shard.unbound", ShardHelper.getKillsFromShard(stack)));
        }
    }
}
