package tehnut.soulshards.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import tehnut.soulshards.ConfigHandler;

public class EnchantmentSoulStealer extends Enchantment {

    public EnchantmentSoulStealer() {
        super(ConfigHandler.soulStealerEnchantID, ConfigHandler.soulStealerWeight, EnumEnchantmentType.all);
        this.name = "enchantment.SoulShards.soulStealer";
    }

    @Override
    public int getMinEnchantability(int level) {
        return (level - 1) * 11;
    }

    @Override
    public int getMaxEnchantability(int level) {
        return this.getMinEnchantability(level) + 20;
    }

    @Override
    public int getMaxLevel() {
        return 5;
    }
}
