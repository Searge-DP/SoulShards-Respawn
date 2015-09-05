package tehnut.soulshards.util;

import cpw.mods.fml.common.Loader;
import tehnut.soulshards.util.helper.LogHelper;

public class Utils {

    /**
     * Loads a class if the given modid is found
     *
     * @param clazz - Compatibility class
     * @param modid - Required modid
     */
    public static void registerCompat(Class clazz, String modid) {
        if (Loader.isModLoaded(modid)) {
            try {
                Class.forName(clazz.getCanonicalName());
            } catch (ClassNotFoundException e) {
                LogHelper.error("Could not find compatibility class for mod { " + modid + " }. Please report this.");
            }
        }
    }
}
