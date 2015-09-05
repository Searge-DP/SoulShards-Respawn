package tehnut.soulshards.util.helper;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import tehnut.soulshards.ConfigHandler;

public class ItemHelper {

    public static int getSoulStealerBonus(ItemStack stack) {
        return EnchantmentHelper.getEnchantmentLevel(ConfigHandler.soulStealerEnchantID, stack) * ConfigHandler.soulStealerBonus;
    }
}
