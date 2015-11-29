package tehnut.soulshards;

import net.minecraftforge.common.config.Configuration;
import tehnut.soulshards.enumeration.EnumTier;

import java.io.File;

public class ConfigHandler {

    public static Configuration config;

    // Settings
    public static boolean allowBossSpawners;
    public static boolean pansyMode;
    public static String listType;

    public static boolean enableConsoleLogging;
    public static boolean enableVerboseErrorReporting;

    public static int soulStealerEnchantID;
    public static int soulStealerBonus;
    public static int soulStealerWeight;

    public static int[] killRequirement = { 64, 128, 256, 512, 1024 };
    public static int[] maxSpawns = { 2, 4, 4, 4, 6 };
    public static int[] spawnDelay = { 400, 200, 100, 100, 50 };
    public static boolean[] requirePlayer = { true, true, false, false, false };
    public static boolean[] requireDark = { true, true, true, true, false };
    public static boolean[] checkRedstone = { false, false, false, false, true };

    public static void init(File file) {
        config = new Configuration(file);
        syncConfig();
    }

    public static void syncConfig() {
        String category;

        category = "Balance";
        config.addCustomCategoryComment(category, "General balancing tweaks.");
        allowBossSpawners = config.getBoolean("allowBossSpawners", category, false, "Allows spawners to be created for bosses. You will not have a good day.");
        pansyMode = config.getBoolean("pansyMode", category, false, "Soul Shards are crafted using the old \"Multiblock\" method.");
        listType = config.getString("listType", category, "BLACKLIST", "The setting used for the mob Blacklist/Whitelist.\nValid entries are:\n`BLACKLIST` - All mobs listed will *not* be registered.\n`WHITELIST` - Only mobs listed will be registered.");


        category = "General";
        config.addCustomCategoryComment(category, "General settings that don't fit elsewhere.");
        enableConsoleLogging = config.getBoolean("enableConsoleLogging", category, true, "Allows the mod to log things to the console. Keeping this enabled will allow support to go much smoother.");
        enableVerboseErrorReporting = config.getBoolean("enableVerboseErrorReporting", category, false, "If you need to find out where something went wrong, enable this. Errors will be logged much more frequently.");

        category = "Enchantment";
        config.addCustomCategoryComment(category, "Enchantment settings");
        soulStealerEnchantID = config.getInt("soulStealerEnchantID", category + ".soulStealer", 102, 0, 255, "ID to use for the Soul Stealer enchantment.");
        soulStealerBonus = config.getInt("soulStealerBonus", category + ".soulStealer", 52, 1, 10, "Bonus multiplier for the Soul Stealer enchantment. The formula is: Base + (Level * Bonus)");
        soulStealerWeight = config.getInt("soulStealerWeight", category + ".soulStealer", 8, 1, 10, "The weight of the Soul Stealer enchantment in the enchanting table.");

        category = "Tiers";
        config.addCustomCategoryComment(category, "Settings for each individual tier.");
        doTierConfig(EnumTier.ONE);
        doTierConfig(EnumTier.TWO);
        doTierConfig(EnumTier.THREE);
        doTierConfig(EnumTier.FOUR);
        doTierConfig(EnumTier.FIVE);

        config.save();
    }

    private static void doTierConfig(EnumTier tier) {
        String category = "Tiers." + tier.toCasedString();

        killRequirement[tier.getNumber()] = config.getInt("killRequirement", category, killRequirement[tier.getNumber()], 0, Integer.MAX_VALUE, "The required amount of kills to obtain the shard for this tier.");
        maxSpawns[tier.getNumber()] = config.getInt("maxSpawns", category, maxSpawns[tier.getNumber()], 0, Integer.MAX_VALUE, "The max amount of mobs this tier can spawn at a time.");
        spawnDelay[tier.getNumber()] = config.getInt("spawnDelay", category, spawnDelay[tier.getNumber()], 0, Integer.MAX_VALUE, "The amount of time to wait between spawn attempts in ticks.\n20 Ticks = 1 Second");
        requirePlayer[tier.getNumber()] = config.getBoolean("requirePlayer", category, requirePlayer[tier.getNumber()], "Whether a player is required to be in range or not.");
        requireDark[tier.getNumber()] = config.getBoolean("requireDark", category, requireDark[tier.getNumber()], "Whether the spawner ignores light or not. If false, follows vanilla spawner rules.");
        checkRedstone[tier.getNumber()] = config.getBoolean("checkRedstone", category, checkRedstone[tier.getNumber()], "Whether the spawner should react to Redstone signals.");
    }

    private static void doListConfig() {

    }
}
