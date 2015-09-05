package tehnut.soulshards.registry;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;
import tehnut.soulshards.item.ItemMaterial;
import tehnut.soulshards.item.ItemShard;

public class ItemRegistry {

    public static Item shard;
    public static Item material;

    public static void registerItems() {
        shard = new ItemShard();
        GameRegistry.registerItem(shard, shard.getClass().getSimpleName());

        material = new ItemMaterial();
        GameRegistry.registerItem(material, material.getClass().getSimpleName());
    }
}
