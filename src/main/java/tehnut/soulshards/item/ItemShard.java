package tehnut.soulshards.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import tehnut.soulshards.ModInformation;
import tehnut.soulshards.enumeration.EnumTier;
import tehnut.soulshards.util.helper.ShardHelper;

import java.util.List;

public class ItemShard extends Item {

    public ItemShard() {
        super();

        setUnlocalizedName(ModInformation.ID + ".shard");
        setTextureName("minecraft:emerald");
        setCreativeTab(CreativeTabs.tabTools);
        setMaxStackSize(1);
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        EnumTier tier = ShardHelper.getTierFromShard(stack);

        if (tier != EnumTier.DEFAULT)
            return super.getUnlocalizedName(stack) + "." + tier.toString();

        return super.getUnlocalizedName(stack);
    }

    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tabs, List list) {
        for (EnumTier tier : EnumTier.values())
            if (tier != EnumTier.DEFAULT)
                list.add(ShardHelper.setTierForShard(new ItemStack(this), tier));

        list.add(ShardHelper.setKillsForShard(new ItemStack(this), 200));
    }

    @Override
    public boolean hasEffect(ItemStack stack, int pass) {
        return ShardHelper.getTierFromShard(stack) == EnumTier.FIVE;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean advanced) {
        list.add(StatCollector.translateToLocalFormatted("tooltip.soulshards.shard.tier", ShardHelper.getTierFromShard(stack)));
        list.add(StatCollector.translateToLocalFormatted("tooltip.soulshards.shard.kill", ShardHelper.getKillsFromShard(stack)));
    }
}
