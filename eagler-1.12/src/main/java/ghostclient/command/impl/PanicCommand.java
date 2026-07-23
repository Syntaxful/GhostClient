package ghostclient.command.impl;

import ghostclient.GhostClient;
import ghostclient.command.Command;
import ghostclient.module.Module;

public class PanicCommand extends Command {

    public PanicCommand() {
        super("panic", "Disable every module immediately.");
    }

    @Override
    public String execute(String[] args) {
        int count = 0;
        for (Module module : GhostClient.MODULES.getAll()) {
            if (module.isEnabled()) {
                module.setEnabled(false);
                count++;
            }
        }
        return "\u00a7cDisabled " + count + " modules.";
    }
}
