package tehnut.soulshards.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import tehnut.soulshards.ModInformation;
import tehnut.soulshards.SoulShardsRespawn;

import java.util.List;

public class ItemMaterial extends Item {

    String[] names = { "nugget.soulium", "ingot.soulium", "dust.vile", "essence.corrupted", "petrified.stick"};
    IIcon[] icon = new IIcon[names.length];

    public ItemMaterial() {
        super();

        setUnlocalizedName(ModInformation.UNLOC + ".material");
        setCreativeTab(SoulShardsRespawn.tabSoulShards);
        setHasSubtypes(true);
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return getUnlocalizedName() + "." + names[stack.getItemDamage() % names.length];
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int meta) {
        return this.icon[meta];
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister) {
        this.icon[0] = iconRegister.registerIcon(ModInformation.DOMAIN + "nuggetSoulium");
        this.icon[1] = iconRegister.registerIcon(ModInformation.DOMAIN + "ingotSoulium");
        this.icon[2] = iconRegister.registerIcon(ModInformation.DOMAIN + "dustVile");
        this.icon[3] = iconRegister.registerIcon(ModInformation.DOMAIN + "essenceCorrupted");
        this.icon[4] = iconRegister.registerIcon(ModInformation.DOMAIN + "stickPetrified");
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tabs, List list) {
        for (int i = 0; i < names.length; i++)
            list.add(new ItemStack(this, 1, i));
    }
}
