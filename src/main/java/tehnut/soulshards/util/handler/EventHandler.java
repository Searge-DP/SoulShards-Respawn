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
import tehnut.soulshards.enumeration.EnumTier;
import tehnut.soulshards.util.EntityMapper;
import tehnut.soulshards.util.helper.ItemHelper;
import tehnut.soulshards.util.helper.LogHelper;
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

        if (!EntityMapper.isValidName(entityName))
            return;

        // Special cases :(
        if (dead instanceof EntitySkeleton && ((EntitySkeleton) dead).getSkeletonType() == 1)
            entityName = "Wither Skeleton";

        ItemStack shard = ShardHelper.getShardStackWithEntity(player, entityName);

        if (shard == null)
            shard = ShardHelper.getShardStackFromPlayer(player);

        if (shard != null) {
            if (!ShardHelper.hasEntity(shard))
                shard = ShardHelper.setEntityForShard(shard, entityName);

            if (!ShardHelper.getEntityFromShard(shard).equals(entityName))
                return;

            if (ShardHelper.getKillsFromShard(shard) < EnumTier.FIVE.getBound().getMax())
                shard = ShardHelper.increaseKillCount(shard, 1 + ItemHelper.getSoulStealerBonus(player.getHeldItem()));

            int firstShardSlot = ShardHelper.getFirstShardForEntity(player.inventory, entityName);

            if (firstShardSlot == -1)
                firstShardSlot = ShardHelper.getFirstShard(player.inventory);

            if (firstShardSlot >= 0 && firstShardSlot <= player.inventory.getSizeInventory())
                player.inventory.setInventorySlotContents(firstShardSlot, shard);
        }
    }
}
