package tehnut.soulshards.registry;

import net.minecraft.item.ItemStack;
import tehnut.soulshards.util.handler.SoulForgeRecipeHandler;

public class RecipeRegistry {

    public static void registerSoulForgeRecipes() {
        SoulForgeRecipeHandler.registerRecipe("ingotIron", new ItemStack(ItemRegistry.material, 1, 0), 24000);
    }

    public static void registerSoulForgeFuels() {
        SoulForgeRecipeHandler.registerFuel(new ItemStack(ItemRegistry.material, 1, 3), 6000);
    }
}
