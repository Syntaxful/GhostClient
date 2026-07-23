package ghostclient.module;

/**
 * Module category. Matches the ClickGUI sidebar tabs.
 */
public enum Category {
    Combat("Combat"),
    Movement("Movement"),
    Player("Player"),
    Render("Render"),
    World("World"),
    Misc("Misc");

    private final String displayName;

    Category(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
