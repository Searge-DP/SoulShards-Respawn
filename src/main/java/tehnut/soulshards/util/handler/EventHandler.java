package tehnut.soulshards.util.handler;

import com.google.common.base.Strings;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import tehnut.soulshards.util.helper.ShardHelper;

public class EventHandler {

    @SubscribeEvent
    public void onEntityDeath(LivingDeathEvent event) {

        World world = event.entity.worldObj;

        if (world.isRemote || !(event.entity instanceof EntityLiving) || !(event.source.getEntity() instanceof EntityPlayer) || (event.source.getEntity() instanceof FakePlayer))
            return;

        EntityLiving dead = (EntityLiving) event.entity;
        EntityPlayer player = (EntityPlayer) event.source.getEntity();
        String entityName = EntityList.getEntityString(dead);

        if (Strings.isNullOrEmpty(entityName))
            return;

        if (dead instanceof EntitySkeleton && ((EntitySkeleton) dead).getSkeletonType() == 1)
            entityName = "Wither Skeleton";

        ItemStack shard = ShardHelper.getShardStackFromPlayer(player);

        if (shard != null) {
            if (!ShardHelper.isShardBound(shard))
                ShardHelper.setEntityForShard(shard, entityName);

            ShardHelper.increaseKillCount(shard);
        }
    }
}
