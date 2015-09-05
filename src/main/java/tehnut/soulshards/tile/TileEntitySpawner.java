package tehnut.soulshards.tile;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import tehnut.soulshards.item.ItemShard;
import tehnut.soulshards.util.EntityMapper;
import tehnut.soulshards.util.helper.ShardHelper;

@Setter
@Getter
@Accessors(chain = true)
public class TileEntitySpawner extends TileEntity implements IInventory {

    ItemStack[] inventory = new ItemStack[1];

    public TileEntitySpawner() {
        super();
    }

    @Override
    public void updateEntity() {
        ItemStack shard = getStackInSlot(0);

        if (shard == null)
            return;

        if (shard.getItem() instanceof ItemShard && ShardHelper.hasEntity(shard))
            if (!getWorld().isRemote)
                doSpawn(EntityMapper.createInstance(getWorld(), ShardHelper.getEntityFromShard(shard)), shard);
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        NBTTagList tags = new NBTTagList();

        for (int i = 0; i < inventory.length; i++) {
            if (inventory[i] != null) {
                NBTTagCompound data = new NBTTagCompound();
                data.setByte("Slot", (byte) i);
                inventory[i].writeToNBT(data);
                tags.appendTag(data);
            }
        }

        nbt.setTag("Items", tags);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        NBTTagList tags = nbt.getTagList("Items", 10);
        inventory = new ItemStack[getSizeInventory()];

        for (int i = 0; i < tags.tagCount(); i++) {
            NBTTagCompound data = tags.getCompoundTagAt(i);
            int j = data.getByte("Slot") & 255;

            if (j >= 0 && j < inventory.length) {
                inventory[j] = ItemStack.loadItemStackFromNBT(data);
            }
        }
    }

    @Override
    public Packet getDescriptionPacket() {
        NBTTagCompound tag = new NBTTagCompound();
        writeToNBT(tag);
        return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 1, tag);
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity packet) {
        readFromNBT(packet.func_148857_g());
    }

    public World getWorld() {
        return this.worldObj;
    }

    public void doSpawn(EntityLiving entity, ItemStack shard) {
        if (entity == null)
            return;

        if (canSpawn(entity))
            ShardHelper.getTierFromShard(shard).doSpawn(getWorld(), entity, xCoord, yCoord, zCoord);
    }

    private boolean canSpawn(EntityLiving entity) {
        return getWorld().getCollidingBoundingBoxes(entity, entity.boundingBox).isEmpty();
    }

    // IInventory

    @Override
    public int getSizeInventory() {
        return 1;
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        return inventory[slot];
    }

    @Override
    public ItemStack decrStackSize(int slot, int amount) {
        return null;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int slot) {
        return inventory[slot];
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack stack) {
        inventory[slot] = stack;
    }

    @Override
    public String getInventoryName() {
        return null;
    }

    @Override
    public boolean hasCustomInventoryName() {
        return false;
    }

    @Override
    public int getInventoryStackLimit() {
        return 1;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player) {
        return false;
    }

    @Override
    public void openInventory() {

    }

    @Override
    public void closeInventory() {

    }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack stack) {
        return stack != null && stack.getItem() instanceof ItemShard && ShardHelper.hasEntity(stack);
    }
}
