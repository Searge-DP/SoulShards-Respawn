package tehnut.soulshards.tile;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import tehnut.soulshards.enumeration.EnumTier;
import tehnut.soulshards.registry.ItemRegistry;
import tehnut.soulshards.util.helper.ShardHelper;

@Setter
@Getter
@Accessors(chain = true)
public class TileEntitySpawner extends TileEntity {

    private EnumTier shardTier = EnumTier.DEFAULT;
    private ItemStack shardIn = ShardHelper.setTierForShard(new ItemStack(ItemRegistry.shard), EnumTier.DEFAULT);

    public TileEntitySpawner() {
        super();
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);
    }

    @Override
    public Packet getDescriptionPacket() {
        NBTTagCompound tagCompound = new NBTTagCompound();
        this.writeToNBT(tagCompound);

        return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 1, tagCompound);
    }

    @Override
    public void onDataPacket(NetworkManager manager, S35PacketUpdateTileEntity packet) {
        this.readFromNBT(packet.func_148857_g());
    }

    public World getWorld() {
        return this.worldObj;
    }

    public void resetTier() {
        this.setShardTier(EnumTier.DEFAULT);
        this.setShardIn(ShardHelper.setTierForShard(new ItemStack(ItemRegistry.shard), EnumTier.DEFAULT));
    }
}
