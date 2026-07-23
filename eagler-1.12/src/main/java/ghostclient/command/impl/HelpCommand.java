package ghostclient.command.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ghostclient.command.Command;
import ghostclient.command.CommandManager;

public class HelpCommand extends Command {

    public HelpCommand() {
        super("help", "List all commands.");
    }

    @Override
    public String execute(String[] args) {
        Map<String, ghostclient.command.Command> commands = CommandManager.getCommands();
        List<String> names = new ArrayList<>(commands.keySet());
        java.util.Collections.sort(names);
        StringBuilder sb = new StringBuilder("\u00a7aCommands:\u00a7r");
        for (String name : names) {
            sb.append(" \u00a7b.").append(name).append("\u00a7r");
        }
        return sb.toString();
    }
}
