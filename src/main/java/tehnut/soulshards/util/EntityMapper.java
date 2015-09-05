package tehnut.soulshards.util;

import cpw.mods.fml.common.registry.VillagerRegistry;
import lombok.Getter;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.world.World;
import tehnut.soulshards.util.helper.LogHelper;

import java.util.ArrayList;
public class EntityMapper {

    @Getter
    private static ArrayList<String> entityNames = new ArrayList<String>();

    public static void registerEntity(String entityName) {
        if (!entityNames.contains(entityName))
            entityNames.add(entityName);
        else
            LogHelper.error("Entity already registered.");
    }

    public static boolean isValidName(String name) {
        return entityNames.contains(name);
    }

    public static EntityLiving createInstance(World world, String name) {
        // Special cases :(
        if (name.equals("Wither Skeleton")) {
            EntitySkeleton skeleton = new EntitySkeleton(world);
            skeleton.setSkeletonType(1);
            return skeleton;
        } else if (name.equals("Villager")) {
            EntityVillager villager = new EntityVillager(world);
            VillagerRegistry.applyRandomTrade(villager, villager.worldObj.rand);
            return villager;
        }

        return (EntityLiving) EntityList.createEntityByName(name, world);
    }
}
