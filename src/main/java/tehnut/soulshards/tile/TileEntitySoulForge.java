package tehnut.soulshards.tile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.StatCollector;

public class TileEntitySoulForge extends TileEntity implements ISidedInventory {

    private ItemStack[] inventory = new ItemStack[3];

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
        return slot != 2;
    }
}
