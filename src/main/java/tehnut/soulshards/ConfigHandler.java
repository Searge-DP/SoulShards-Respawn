package tehnut.soulshards;

import net.minecraftforge.common.config.Configuration;
import tehnut.soulshards.enumeration.EnumTier;

import java.io.File;

public class ConfigHandler {

    public static Configuration config;

    // Settings
    public static boolean enableConsoleLogging;

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

        category = "General";
        config.addCustomCategoryComment(category, "General settings that don't fit elsewhere.");
        enableConsoleLogging = config.getBoolean("enableConsoleLogging", category, true, "Allows the mod to log things to the console. Keeping this enabled will allow support to go much smoother.");

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
}
