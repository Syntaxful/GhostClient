package ghostclient.command.impl;

import ghostclient.command.Command;
import ghostclient.config.ConfigManager;

public class ConfigCommand extends Command {

    public ConfigCommand() {
        super("config", "Save or load config. Usage: .config <save|load>");
    }

    @Override
    public String execute(String[] args) {
        if (args.length < 1) return "\u00a7cUsage: .config <save|load>";
        String action = args[0].toLowerCase();
        if (action.equals("save")) {
            ConfigManager.save();
            return "\u00a7aConfig saved.";
        } else if (action.equals("load")) {
            ConfigManager.load();
            return "\u00a7aConfig loaded.";
        } else {
            return "\u00a7cUsage: .config <save|load>";
        }
    }
}
