package tehnut.soulshards.proxy;

import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import tehnut.soulshards.client.gui.GuiSoulForge;
import tehnut.soulshards.client.gui.container.ContainerSoulForge;
import tehnut.soulshards.tile.TileEntitySoulForge;

public class CommonProxy implements IGuiHandler {

    public void load() {

    }

    public void registerCommands() {

    }

    @Override
    public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
        switch (id) {
            case 0: return new ContainerSoulForge(player.inventory, (TileEntitySoulForge) world.getTileEntity(x, y, z));
        }

        return null;
    }

    @Override
    public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
        switch (id) {
            case 0: return new GuiSoulForge(player.inventory, (TileEntitySoulForge) world.getTileEntity(x, y, z));
        }

        return null;
    }
}
