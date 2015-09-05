package tehnut.soulshards.proxy;

import net.minecraftforge.client.ClientCommandHandler;
import tehnut.soulshards.command.CommandPrintEntities;

public class ClientProxy extends CommonProxy {

    @Override
    public void load() {

    }

    @Override
    public void registerCommands() {
        ClientCommandHandler.instance.registerCommand(new CommandPrintEntities());
    }
}
