package tehnut.soulshards.util.handler;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import tehnut.soulshards.util.helper.LogHelper;

import java.util.HashMap;
import java.util.Map;

public class SoulForgeRecipeHandler {

    private static final SoulForgeRecipeHandler forgeBase = new SoulForgeRecipeHandler();
    // Input -> Output
    private Map<ItemStack, ItemStack> recipeMap = new HashMap<ItemStack, ItemStack>();
    // Output -> Ticks
    private Map<ItemStack, Integer> timeMap = new HashMap<ItemStack, Integer>();
    // Fuel -> Ticks lasted
    private Map<ItemStack, Integer> fuelMap = new HashMap<ItemStack, Integer>();

    public static SoulForgeRecipeHandler getForgeBase() {
        return forgeBase;
    }

    public static void registerRecipe(ItemStack input, ItemStack output, int ticks) {
        getForgeBase().recipeMap.put(input, output);
        getForgeBase().timeMap.put(output, ticks);
    }

    public static void registerRecipe(String input, ItemStack output, int ticks) {
        if (OreDictionary.doesOreNameExist(input))
            registerRecipe(OreDictionary.getOres(input).get(0), output, ticks);
        else
            LogHelper.error("Error registering Soul Forge recipe { " + input + " -> " + output.getDisplayName() + " }. Ore name does not exist.");
    }

    public static void registerFuel(ItemStack stack, int ticks) {
        if (!getForgeBase().fuelMap.containsKey(stack))
            getForgeBase().fuelMap.put(stack, ticks);
        else
            LogHelper.error("Error registering fuel { " + stack.getDisplayName() + " } for { " + ticks + " } ticks. It already exists.");
    }
}
