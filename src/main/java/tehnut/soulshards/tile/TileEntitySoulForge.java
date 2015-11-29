package tehnut.soulshards.tile;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.StatCollector;
import tehnut.soulshards.util.handler.SoulForgeRecipeHandler;

public class TileEntitySoulForge extends TileEntity implements ISidedInventory {

    public int forgeBurn;
    public int currentBurn;
    public int forgeCook;
    ItemStack[] inventory = new ItemStack[3];

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

        forgeBurn = nbt.getInteger("burn");
        forgeCook = nbt.getInteger("cook");
        currentBurn = nbt.getInteger("currentBurn");
    }

    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);

        nbt.setInteger("burn", forgeBurn);
        nbt.setInteger("cook", forgeCook);
        nbt.setInteger("currentBurn", currentBurn);

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
    
    @SideOnly(Side.CLIENT)
    public int getCookProgressScaled(int val1) {
        return forgeCook * val1 / this.smeltTime();
    }

    @SideOnly(Side.CLIENT)
    public int getBurnTimeRemainingScaled(int val1) {
        if (currentBurn == 0)
            currentBurn = smeltTime();

        return forgeBurn * val1 / currentBurn;
    }

    public boolean isBurning() {
        return forgeBurn > 0;
    }

    public int smeltTime() {
        if (getStackInSlot(0) == null) {
            return 200;
        } else {
            ItemStack output = SoulForgeRecipeHandler.getResult(getStackInSlot(0));
            int smeltTime = SoulForgeRecipeHandler.getRecipeHandler().getTimeMap().get(output);

            if (smeltTime == 0)
                return 200;

            return smeltTime;
        }
    }

    private boolean canSmelt() {
        if (getStackInSlot(0) == null)
            return false;

        ItemStack output = SoulForgeRecipeHandler.getResult(getStackInSlot(0));
        if (output == null)
            return false;

        if (getStackInSlot(2) == null)
            return true;

        if (!getStackInSlot(2).isItemEqual(output))
            return false;

        int result = getStackInSlot(2).stackSize + output.stackSize;

        return result <= getInventoryStackLimit() && result <= getStackInSlot(2).getMaxStackSize();
    }

    public void smeltItem() {
        if (canSmelt()) {
            ItemStack output = SoulForgeRecipeHandler.getResult(getStackInSlot(0));

            if (getStackInSlot(2) == null)
                inventory[2] = output.copy();
            else if (getStackInSlot(2).getItem() == output.getItem() && getStackInSlot(2).getItemDamage() == output.getItemDamage())
                inventory[2].stackSize += output.stackSize;

            --getStackInSlot(0).stackSize;

            if (getStackInSlot(0).stackSize <= 0)
                inventory[0] = null;
        }
    }

    public int getItemBurnTime(ItemStack stack) {
        return SoulForgeRecipeHandler.getRecipeHandler().getFuelMap().get(stack);
    }

    // ISidedInventory

    @Override
    public int[] getAccessibleSlotsFromSide(int slot) {

        int[] top = { 0 };
        int[] bottom = { 2, 1 };
        int[] side = { 1 };

        return slot == 0 ? bottom : (slot == 1 ? top : side);
    }

    @Override
    public boolean canInsertItem(int slot, ItemStack stack, int side) {
        return this.isItemValidForSlot(slot, stack);
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack stack, int side) {
        return side != 0 || slot != 1 || stack.getItem() == Items.bucket;
    }

    // IInventory

    @Override
    public int getSizeInventory() {
        return inventory.length;
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        return this.inventory[slot];
    }

    @Override
    public ItemStack decrStackSize(int slot, int amount) {
        if (this.inventory[slot] != null) {
            ItemStack itemstack;
            if (this.inventory[slot].stackSize <= amount) {
                itemstack = this.inventory[slot];
                this.inventory[slot] = null;
                return itemstack;
            } else {
                itemstack = this.inventory[slot].splitStack(amount);
                if (this.inventory[slot].stackSize == 0)
                    this.inventory[slot] = null;

                return itemstack;
            }
        } else {
            return null;
        }
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int slot) {
        if (this.inventory != null) {
            ItemStack ret = this.inventory[slot];
            this.inventory[slot] = null;
            return ret;
        } else {
            return null;
        }
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack stack) {
        if (stack != null && slot <= inventory.length)
            this.inventory[slot] = stack;
    }

    @Override
    public String getInventoryName() {
        return StatCollector.translateToLocal("gui.soulshards.forge.name");
    }

    @Override
    public boolean hasCustomInventoryName() {
        return true;
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player) {
        return true;
    }

    @Override
    public void openInventory() {

    }

    @Override
    public void closeInventory() {

    }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack stack) {
        if (slot == 1)
            return SoulForgeRecipeHandler.isFuel(stack);

        return slot != 2;
    }
}
