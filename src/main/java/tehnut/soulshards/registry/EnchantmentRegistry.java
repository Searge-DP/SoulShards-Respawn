package tehnut.soulshards.registry;

import net.minecraft.enchantment.Enchantment;
import tehnut.soulshards.enchantment.EnchantmentSoulStealer;

public class EnchantmentRegistry {

    public static Enchantment soulStealer;

    public static void registerEnchantments() {
        soulStealer = new EnchantmentSoulStealer();
        Enchantment.addToBookList(soulStealer);
    }
}
