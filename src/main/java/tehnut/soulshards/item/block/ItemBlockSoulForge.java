package tehnut.soulshards.item.block;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

import java.util.List;

public class ItemBlockSoulForge extends ItemBlock {

    public ItemBlockSoulForge(Block block) {
        super(block);

        setHasSubtypes(true);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void getSubItems(Item item, CreativeTabs tabs, List list) {
        list.add(new ItemStack(this, 1, 3));
    }
}
