package tehnut.soulshards.util.helper;

import com.google.common.base.Strings;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import tehnut.soulshards.enumeration.EnumTier;
import tehnut.soulshards.registry.ItemRegistry;
import tehnut.soulshards.util.Bound;

public class ShardHelper {

    public static final String NBT_KILL = "kills";
    public static final String NBT_ENTITY = "entity";

    public static EnumTier getTierFromShard(ItemStack stack) {
        if (stack.getTagCompound() == null)
            stack.stackTagCompound = new NBTTagCompound();

        int kills = getKillsFromShard(stack);
        EnumTier ret = EnumTier.UNBOUND;

        for (EnumTier tier : EnumTier.values())
            if (boundContainsKills(tier.getBound(), kills))
                return tier;

        return ret;
    }

    public static int getKillsFromShard(ItemStack stack) {
        if (stack.getTagCompound() == null)
            stack.stackTagCompound = new NBTTagCompound();

        return stack.getTagCompound().getInteger(NBT_KILL);
    }

    public static boolean holdingShard(EntityPlayer player) {
        return player.inventory.hasItem(ItemRegistry.shard);
    }

    public static int getFirstShard(IInventory inventory) {
        for (int slot = 0; slot < inventory.getSizeInventory(); ++slot)
            if (inventory.getStackInSlot(slot)!= null && inventory.getStackInSlot(slot).getItem() == ItemRegistry.shard)
                return slot;

        return -1;
    }

    public static int getFirstShardForEntity(IInventory inventory, String entity) {
        for (int slot = 0; slot < inventory.getSizeInventory(); ++slot)
            if (inventory.getStackInSlot(slot)!= null && inventory.getStackInSlot(slot).getItem() == ItemRegistry.shard) {
                if (hasEntity(inventory.getStackInSlot(slot)) && getEntityFromShard(inventory.getStackInSlot(slot)).equals(entity))
                    return slot;
            }

        return -1;
    }

    public static ItemStack getShardStackFromPlayer(EntityPlayer player) {
        return holdingShard(player) ? player.inventory.getStackInSlot(getFirstShard(player.inventory)) : null;
    }

    public static ItemStack getShardStackWithEntity(EntityPlayer player, String entity) {
        for (int slot = 0; slot < player.inventory.getSizeInventory(); ++slot) {
            ItemStack slotStack = player.inventory.getStackInSlot(slot);

            if (slotStack != null && slotStack.getItem() == ItemRegistry.shard) {
                if (getEntityFromShard(slotStack).equals(entity))
                    return slotStack;
            }
        }

        return null;
    }

    public static String getEntityFromShard(ItemStack stack) {
        if (stack.getTagCompound() == null)
            stack.stackTagCompound = new NBTTagCompound();

        return stack.getTagCompound().getString(NBT_ENTITY);
    }

    public static ItemStack setTierForShard(ItemStack stack, EnumTier tier) {
        if (stack.getTagCompound() == null)
            stack.stackTagCompound = new NBTTagCompound();

        ItemStack ret = stack.copy();
        ret.getTagCompound().setInteger(NBT_KILL, tier.getBound().getMin());

        return ret;
    }

    private static ItemStack setKillsForShard(ItemStack stack, int kills) {
        if (stack.getTagCompound() == null)
            stack.stackTagCompound = new NBTTagCompound();

        ItemStack ret = stack.copy();
        ret.getTagCompound().setInteger(NBT_KILL, kills);

        return ret;
    }

    public static ItemStack setEntityForShard(ItemStack stack, String entityName) {
        if (stack.getTagCompound() == null)
            stack.stackTagCompound = new NBTTagCompound();

        ItemStack ret = stack.copy();
        ret.getTagCompound().setString(NBT_ENTITY, entityName);

        return ret;
    }

    public static boolean hasEntity(ItemStack stack) {
        if (stack.getTagCompound() == null)
            stack.stackTagCompound = new NBTTagCompound();

        return !Strings.isNullOrEmpty(stack.getTagCompound().getString(NBT_ENTITY));
    }

    public static boolean boundContainsKills(Bound<Integer> bound, int kills) {
        return bound.getMin() <= kills && bound.getMax() >= kills;
    }

    public static ItemStack increaseKillCount(ItemStack stack, int amount) {
        if (stack.getTagCompound() == null)
            stack.stackTagCompound = new NBTTagCompound();

        ItemStack ret = stack.copy();
        int kills = getKillsFromShard(ret);
        ret = setKillsForShard(ret, kills + amount);

        return ret;
    }

    public static ItemStack increaseKillCount(ItemStack stack) {
        return increaseKillCount(stack, 1);
    }

    public static boolean isBound(ItemStack stack) {
        if (stack.getTagCompound() == null)
            stack.stackTagCompound = new NBTTagCompound();

        return stack.getTagCompound().getInteger(NBT_KILL) > 0 || getTierFromShard(stack) != EnumTier.UNBOUND;
    }
}
