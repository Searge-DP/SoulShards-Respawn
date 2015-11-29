package tehnut.soulshards.client.gui.container;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnace;
import net.minecraft.item.ItemStack;
import tehnut.soulshards.tile.TileEntitySoulForge;
import tehnut.soulshards.util.handler.SoulForgeRecipeHandler;

public class ContainerSoulForge extends Container {

    TileEntitySoulForge soulForge;
    private int lastCookTime;
    private int lastBurnTime;
    private int lastItemBurnTime;

    public ContainerSoulForge(InventoryPlayer inventoryPlayer, TileEntitySoulForge soulForge) {
        this.soulForge = soulForge;
        this.addSlotToContainer(new Slot(soulForge, 0, 56, 17));
        this.addSlotToContainer(new Slot(soulForge, 1, 56, 53));
        this.addSlotToContainer(new SlotFurnace(inventoryPlayer.player, soulForge, 2, 116, 35));


        int i;

        for (i = 0; i < 3; ++i)
            for (int j = 0; j < 9; ++j)
                this.addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));

        for (i = 0; i < 9; ++i)
            this.addSlotToContainer(new Slot(inventoryPlayer, i, 8 + i * 18, 142));
    }

    public void addCraftingToCrafters(ICrafting craft) {
        super.addCraftingToCrafters(craft);
        craft.sendProgressBarUpdate(this, 0, soulForge.forgeCook);
        craft.sendProgressBarUpdate(this, 1, soulForge.forgeBurn);
        craft.sendProgressBarUpdate(this, 2, soulForge.currentBurn);
    }

    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        for (Object crafter : crafters) {
            if (this.lastCookTime != soulForge.forgeCook)
                ((ICrafting) crafter).sendProgressBarUpdate(this, 0, soulForge.forgeCook);

            if (this.lastBurnTime != soulForge.forgeBurn)
                ((ICrafting) crafter).sendProgressBarUpdate(this, 1, soulForge.forgeBurn);

            if (this.lastItemBurnTime != soulForge.currentBurn)
                ((ICrafting) crafter).sendProgressBarUpdate(this, 2, soulForge.currentBurn);
        }

        this.lastBurnTime = soulForge.forgeBurn;
        this.lastCookTime = soulForge.forgeCook;
        this.lastItemBurnTime = soulForge.currentBurn;
    }

    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int id, int data) {
        switch (id) {
            case 0: soulForge.forgeCook = data; break;
            case 1: soulForge.forgeBurn = data; break;
            case 2: soulForge.currentBurn = data; break;
        }
    }

    public ItemStack transferStackInSlot(EntityPlayer player, int index) {
        ItemStack itemstack = null;
        Slot slot = (Slot) this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack()) {
            ItemStack slotStack = slot.getStack();
            itemstack = slotStack.copy();

            if (index == 2) {
                if (!this.mergeItemStack(slotStack, 3, 39, true))
                    return null;
                slot.onSlotChange(slotStack, itemstack);

            } else if (index != 1 && index != 0) {
                if (SoulForgeRecipeHandler.getResult(slotStack) != null)
                    if (!this.mergeItemStack(slotStack, 0, 1, false))
                        return null;

                else if (SoulForgeRecipeHandler.isFuel(slotStack))
                    if (!this.mergeItemStack(slotStack, 1, 2, false))
                        return null;

                else if (index >= 3 && index < 30)
                    if (!this.mergeItemStack(slotStack, 30, 39, false))
                        return null;

                else if (index >= 30 && index < 39 && !this.mergeItemStack(slotStack, 3, 30, false))
                    return null;

            } else if (!this.mergeItemStack(slotStack, 3, 39, false))
                return null;

            if (slotStack.stackSize == 0)
                slot.putStack(null);
            else
                slot.onSlotChanged();

            if (slotStack.stackSize == itemstack.stackSize)
                return null;

            slot.onPickupFromSlot(player, slotStack);
        }

        return itemstack;
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return soulForge.isUseableByPlayer(player);
    }
}
