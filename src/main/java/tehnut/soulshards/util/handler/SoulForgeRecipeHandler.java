package tehnut.soulshards.util.handler;

import lombok.Getter;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import tehnut.soulshards.util.helper.LogHelper;

import java.util.HashMap;
import java.util.Map;

public class SoulForgeRecipeHandler {

    @Getter
    private static final SoulForgeRecipeHandler recipeHandler = new SoulForgeRecipeHandler();

    // Input -> Output
    @Getter
    private Map<ItemStack, ItemStack> recipeMap = new HashMap<ItemStack, ItemStack>();
    // Output -> Ticks
    @Getter
    private Map<ItemStack, Integer> timeMap = new HashMap<ItemStack, Integer>();
    // Fuel -> Ticks lasted
    @Getter
    private Map<ItemStack, Integer> fuelMap = new HashMap<ItemStack, Integer>();
    // Output -> Fuel items used
    @Getter
    private Map<ItemStack, Integer> burnMap = new HashMap<ItemStack, Integer>();

    public static void registerRecipe(ItemStack input, ItemStack output, int fuelCost, int ticks) {
        getRecipeHandler().getRecipeMap().put(input, output);
        getRecipeHandler().getTimeMap().put(output, ticks);
        getRecipeHandler().getBurnMap().put(output, fuelCost);
    }

    public static void registerRecipe(String input, ItemStack output, int fuelCost, int ticks) {
        if (OreDictionary.doesOreNameExist(input))
            registerRecipe(OreDictionary.getOres(input).get(0), output, fuelCost, ticks);
        else
            LogHelper.error("Error registering Soul Forge recipe { " + input + " -> " + output.getDisplayName() + " }. Ore name does not exist.");
    }

    public static void registerFuel(ItemStack stack, int ticks) {
        if (!getRecipeHandler().getFuelMap().containsKey(stack))
            getRecipeHandler().getFuelMap().put(stack, ticks);
        else
            LogHelper.error("Error registering fuel { " + stack.getDisplayName() + " } for { " + ticks + " } ticks. It already exists.");
    }

    public static ItemStack getResult(ItemStack input) {
        LogHelper.info(input + " --> " + getRecipeHandler().getRecipeMap().get(input));
        return getRecipeHandler().getRecipeMap().get(input);
    }

    public static boolean isFuel(ItemStack fuel) {
        return getRecipeHandler().getFuelMap().containsKey(fuel);
    }
}
