package tehnut.soulshards.registry;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import tehnut.soulshards.util.handler.SoulForgeRecipeHandler;

public class RecipeRegistry {

    public static void registerSoulForgeRecipes() {
        SoulForgeRecipeHandler.registerRecipe(new ItemStack(Items.iron_ingot)/*"ingotIron"*/, new ItemStack(ItemRegistry.material, 1, 0), 1, 24000);
        SoulForgeRecipeHandler.registerRecipe("blockIron", new ItemStack(ItemRegistry.material, 9, 0), 4, 24000 * 8);
    }

    public static void registerSoulForgeFuels() {
        SoulForgeRecipeHandler.registerFuel(new ItemStack(ItemRegistry.material, 1, 3), 6000);
    }
}
