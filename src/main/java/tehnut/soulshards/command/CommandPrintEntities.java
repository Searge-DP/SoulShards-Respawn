package tehnut.soulshards.command;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import tehnut.soulshards.util.EntityMapper;
import tehnut.soulshards.util.helper.LogHelper;

public class CommandPrintEntities extends CommandBase {

    public CommandPrintEntities() {
        super();
    }

    @Override
    public String getCommandName() {
        return "printEntities";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/" + getCommandName();
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        for (String name : EntityMapper.getEntityNames())
            LogHelper.info(name);
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return true;
    }
}
