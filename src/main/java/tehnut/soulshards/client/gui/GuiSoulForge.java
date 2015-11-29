package tehnut.soulshards.client.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import tehnut.soulshards.client.gui.container.ContainerSoulForge;
import tehnut.soulshards.tile.TileEntitySoulForge;

public class GuiSoulForge extends GuiContainer {

    TileEntitySoulForge soulForge;
    private static final ResourceLocation background = new ResourceLocation("textures/gui/container/furnace.png");

    public GuiSoulForge(InventoryPlayer inventoryPlayer, TileEntitySoulForge soulForge) {
        super(new ContainerSoulForge(inventoryPlayer, soulForge));

        this.soulForge = soulForge;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2) {
        String name = soulForge.getInventoryName();
        this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
        this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 94, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float var1, int var2,
                                                   int var3) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(background);
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
        int i1;

        if (soulForge.isBurning()) {
            i1 = soulForge.getBurnTimeRemainingScaled(12);
            this.drawTexturedModalRect(k + 56, l + 36 + 12 - i1, 176, 12 - i1,
                    14, i1 + 2);
        }

        i1 = soulForge.getCookProgressScaled(24);
        this.drawTexturedModalRect(k + 79, l + 34, 176, 14, i1 + 1, 16);
    }
}
