package ghostclient.command;

/**
 * Base class for chat commands.
 */
public abstract class Command {

    private final String name;
    private final String description;

    public Command(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    /**
     * Execute the command and return a response message for the player.
     */
    public abstract String execute(String[] args);
}
