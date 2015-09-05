package tehnut.soulshards.registry;

import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.boss.IBossDisplayData;
import tehnut.soulshards.ConfigHandler;
import tehnut.soulshards.util.EntityMapper;

import java.util.HashMap;
import java.util.Map;

public class SpawnableRegistry {

    @SuppressWarnings("unchecked")
    public static void registerSpawnables() {
        for (Map.Entry<Class, String> entry : ((HashMap<Class, String>) EntityList.classToStringMapping).entrySet()) {
            if (EntityLiving.class.isAssignableFrom(entry.getKey())) {
                if (!IBossDisplayData.class.isAssignableFrom(entry.getKey()))
                    EntityMapper.registerEntity(entry.getValue());
                else
                    addBoss(entry);
            }
        }

        EntityMapper.registerEntity("Wither Skeleton");
    }

    private static void addBoss(Map.Entry<Class, String> entry) {
        if (ConfigHandler.allowBossSpawners)
            EntityMapper.registerEntity(entry.getValue());
    }
}
