package ghostclient.command.impl;

import ghostclient.GhostClient;
import ghostclient.command.Command;
import ghostclient.module.Module;

public class ToggleCommand extends Command {

    public ToggleCommand() {
        super("toggle", "Toggle a module by name. Usage: .toggle <module>");
    }

    @Override
    public String execute(String[] args) {
        if (args.length < 1) return "\u00a7cUsage: .toggle <module>";
        String name = args[0];
        Module module = GhostClient.MODULES.getByName(name);
        if (module == null) return "\u00a7cModule not found: " + name;
        module.toggle();
        return "\u00a7a" + module.getName() + " is now " + (module.isEnabled() ? "enabled" : "disabled") + ".";
    }
}
