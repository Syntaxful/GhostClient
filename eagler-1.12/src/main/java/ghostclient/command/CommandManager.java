package ghostclient.command;

import java.util.HashMap;
import java.util.Map;

import ghostclient.GhostClient;
import ghostclient.command.impl.BindCommand;
import ghostclient.command.impl.ConfigCommand;
import ghostclient.command.impl.HelpCommand;
import ghostclient.command.impl.PanicCommand;
import ghostclient.command.impl.ToggleCommand;

/**
 * Dispatches chat commands starting with '.'.
 */
public class CommandManager {

    private static final Map<String, Command> commands = new HashMap<>();

    public static void init() {
        register(new ToggleCommand());
        register(new BindCommand());
        register(new HelpCommand());
        register(new ConfigCommand());
        register(new PanicCommand());
    }

    private static void register(Command command) {
        commands.put(command.getName().toLowerCase(), command);
    }

    /**
     * Returns true if the message was a command and was handled.
     */
    public static boolean handle(String message) {
        if (!message.startsWith(".")) return false;

        String[] parts = message.substring(1).trim().split("\\s+");
        if (parts.length == 0) return false;

        String name = parts[0].toLowerCase();
        Command command = commands.get(name);
        if (command == null) {
            print("Unknown command. Use .help for a list.");
            return true;
        }

        String[] args = new String[parts.length - 1];
        System.arraycopy(parts, 1, args, 0, args.length);
        String response = command.execute(args);
        if (response != null && !response.isEmpty()) {
            print(response);
        }
        return true;
    }

    public static Map<String, Command> getCommands() {
        return new HashMap<>(commands);
    }

    private static void print(String message) {
        try {
            net.minecraft.client.Minecraft.getMinecraft().ingameGUI.getChatGUI()
                    .printChatMessage(new net.minecraft.util.text.TextComponentString("\u00a77[GhostClient]\u00a7r " + message));
        } catch (Throwable t) {
            System.out.println("[GhostClient] " + message);
        }
    }
}
